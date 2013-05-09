package com.heroku.logfmt;

import java.util.HashMap;
import java.util.Map;

public class Logfmt {

    private static final char SEPARATOR = ' ';

    private static final int KEY_START  = 0;
    private static final int KEY_LEN    = 1;
    private static final int VAL_START  = 2;

    private enum ScanState { NEXT, KEY, VAL }

    public static Map<String, byte[]> parse(byte[] line) {
        ScanState state = ScanState.NEXT;

        Map<String, byte[]> parsed = new HashMap<String, byte[]>();

        boolean quoted = false;
        boolean escaped;

        int[] pos = new int[3];

        for (int i = 0; i < line.length; i++) {
            byte b = line[i];

            switch (state) {
                case NEXT:
                    if (isChar(b)) {
                        state = ScanState.KEY;
                        pos[KEY_START] = i;
                    } else break;
                case KEY:
                    if (b == '=') {
                        quoted = false;
                        pos[KEY_LEN] = i - pos[KEY_START];
                        state = ScanState.VAL;
                        i++;

                        if (i < line.length) {
                            if (line[i] == '"') {
                                quoted = true;
                                i++;
                            }
                            pos[VAL_START] = i;
                            b = line[i];
                        } else {
                            break;
                        }

                    } else if (!isChar(b)) {
                        byte[] key = slice(pos[KEY_START], (i - pos[KEY_START]), line);
                        parsed.put(new String(key), new byte[0]);
                        pos = new int[3];
                        state = ScanState.NEXT;
                        break;
                    }
                case VAL:
                    escaped = false;
                    if (b == '\\' && i < (line.length - 1)) {
                        escaped = true;
                        i++;
                        b = line[i];
                    }
                    if (!isChar(b, quoted, escaped)) {
                        byte[] key = slice(pos[KEY_START], pos[KEY_LEN], line);
                        byte[] value = slice(pos[VAL_START], (i - pos[VAL_START]), line);
                        parsed.put(new String(key), value);
                        state = ScanState.NEXT;
                        pos = new int[3];
                    }
                    break;
            }
        }
        if (pos[KEY_START] + pos[KEY_LEN] > 0) {
            byte[] key = slice(pos[KEY_START], pos[KEY_LEN], line);
            byte[] value = (pos[VAL_START] > 0) ? slice(pos[VAL_START], line.length - pos[VAL_START], line)
                                                : new byte[0];
            parsed.put(new String(key), value);
        }

        return parsed;
    }

    private static boolean isChar(byte b) { return isChar(b, false, false); }

    private static boolean isChar(byte b, boolean quoted, boolean escaped) {
        if (!quoted) return b > SEPARATOR && b != '=' && b != '"';
        else return b >= SEPARATOR && b != '=' && (b != '"' || escaped);
    }

    private static byte[] slice(int start, int len, byte[] a) {
        byte[] n = new byte[len];
        System.arraycopy(a, start, n, 0, len);
        return n;
    }

}
