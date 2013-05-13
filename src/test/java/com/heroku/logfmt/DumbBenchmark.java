package com.heroku.logfmt;

import java.lang.management.ManagementFactory;
import java.math.BigInteger;

public class DumbBenchmark {
    public static void main(String... args) {
        int multiplier = 1;

        char[] data = "foo=bar a=1\\4 baz=\"hello kitty\" cool%story=bro f %^asdf  ".toCharArray();

        if (args.length > 0) multiplier = Integer.valueOf(args[0]);
        if (args.length > 1) data = args[1].toCharArray();

        BigInteger gb = BigInteger.valueOf(2).pow(30).multiply(BigInteger.valueOf(multiplier));
        BigInteger count = gb.divide(BigInteger.valueOf(data.length));

        System.out.print("Warming up...");
        for (int i = 0; i < 100000; i++) {
            Logfmt.parse(data);
        }
        System.out.println("done.");

        printlnf("Timing parsing '%s' (%dbytes) %d times.", new String(data), data.length, count);

        BigInteger time = BigInteger.ZERO;

        for (BigInteger i = BigInteger.ZERO; i.compareTo(count) == -1; i = i.add(BigInteger.ONE)) {
            long start = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
            Logfmt.parse(data);
            time = time.add(BigInteger.valueOf(
                       ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - start
                   ));
        }

        printlnf("Completed %dGB of data in %sms (≈ %dMB/sec)",
                multiplier, time, gb.divide(time.divide(BigInteger.TEN.pow(7)))
                                    .divide(BigInteger.valueOf(2).pow(10)));
    }

    private static void printlnf(String s, Object... args) {
        System.out.println(String.format(s, args));
    }
}
