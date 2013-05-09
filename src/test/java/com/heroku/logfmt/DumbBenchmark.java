package com.heroku.logfmt;

import java.math.BigInteger;

public class DumbBenchmark {
    public static void main(String... args) {
        int multiplier = 1;

        byte[] data = "foo=bar a=1\\4 baz=\"hello kitty\" cool%story=bro f %^asdf  ".getBytes();

        if (args.length > 0) multiplier = Integer.valueOf(args[0]);
        if (args.length > 1) data = args[1].getBytes();

        BigInteger gb = BigInteger.valueOf(2).pow(30).multiply(BigInteger.valueOf(multiplier));
        BigInteger count = gb.divide(BigInteger.valueOf(data.length));

        printlnf("Parsing '%s' (%dbytes) %d times.", new String(data), data.length, count);

        long start = System.currentTimeMillis();

        for (BigInteger i = BigInteger.ZERO; i.compareTo(count) == -1; i = i.add(BigInteger.ONE)) {
            Logfmt.parse(data);
        }

        long time = System.currentTimeMillis() - start;

        printlnf("Completed %dGB of data in %sms (≈ %dMB/sec)",
                 multiplier, time, gb.divide(BigInteger.valueOf(time))
                                     .divide(BigInteger.valueOf(2).pow(10)));
    }

    private static void printlnf(String s, Object... args) {
        System.out.println(String.format(s, args));
    }
}
