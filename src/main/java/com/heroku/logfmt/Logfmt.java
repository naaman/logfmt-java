package com.heroku.logfmt;

import java.util.HashMap;

import java.util.Map;

public class Logfmt implements LogFormat {

    private static char SEPARATOR = ' ';

    private enum ScanState { NEXT, KEY, VAL }

    @Override
    public Map<String, Object> parse(byte[] line) {
        ScanState state = ScanState.NEXT;

        Map<String, Object> parsed = new HashMap<String, Object>();

        boolean quoted = false;

        byte[] key   = new byte[0];
        byte[] value = new byte[0];

        for (int i = 0; i < line.length; i++) {
            byte b = line[i];

            switch (state) {
                case NEXT:
                    if (isChar(b)) state = ScanState.KEY;
                    else break;
                case KEY:
                    if (b == '=') {
                        quoted = false;
                        state = ScanState.VAL;
                        if (i < (line.length - 1) && line[i + 1] == '"') {
                            quoted = true;
                            i++;
                        }
                        break;
                    } else if (isChar(b)) {
                        key = appendbyte(b, key);
                    } else {
                        parsed.put(new String(key), value);
                        state = ScanState.NEXT;
                        key = value = new byte[0];
                    }
                    break;
                case VAL:
                    if (isChar(b, quoted)) {
                        value = appendbyte(b, value);
                    } else {
                        parsed.put(new String(key), value);
                        state = ScanState.NEXT;
                        key = value = new byte[0];
                    }
                    break;
            }
        }
        if (key.length > 0) parsed.put(new String(key), value);

        return parsed;
    }

    private boolean isChar(byte b) { return isChar(b, false); }

    private boolean isChar(byte b, boolean quoted) {
        if (!quoted) return b > SEPARATOR && b != '=' && b != '"';
        else return b >= SEPARATOR && b != '=' && b != '"';
    }

    private byte[] appendbyte(byte b, byte[] a) {
        byte[] n = new byte[a.length + 1];
        if (n.length > 1) System.arraycopy(a, 0, n, 0, n.length - 1);
        n[n.length - 1] = b;
        return n;
    }
}
