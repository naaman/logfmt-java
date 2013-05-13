package com.heroku.logfmt;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

public class CaliperBenchmark extends SimpleBenchmark {
    public static char[] data = "foo=bar a=1\\4 baz=\"hello kitty\" cool%story=bro f %^asdf  ".toCharArray();

    public void timeLogfmtParse(int reps) {
        for (int i = 0; i < reps; i++) {
            Logfmt.parse(data);
        }
    }

    public static void main(String... args) {
        Runner.main(CaliperBenchmark.class, args);
    }
}
