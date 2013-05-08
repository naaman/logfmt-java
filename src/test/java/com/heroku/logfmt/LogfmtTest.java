package com.heroku.logfmt;

import static org.testng.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class LogfmtTest {

    @DataProvider
    public Object[][] linesAsMap() {
        return new Object[][]{
            {
                "   a=foo bar=10ms E=\"12\\\"3\" d foo=",
                new HashMap<String, Object>(){{
                    put("a",   "foo".getBytes());
                    put("bar", "10ms".getBytes());
                    put("E",   "12\"3".getBytes());
                    put("d",   "".getBytes());
                    put("foo", "".getBytes());
                }}
            },
            {
                "foo=bar a=1\\4 baz=\"hello kitty\" cool%story=bro f %^asdf  ",
                new HashMap<String, Object>(){{
                    put("foo",        "bar".getBytes());
                    put("a",          "1\\4".getBytes());
                    put("baz",        "hello kitty".getBytes());
                    put("cool%story", "bro".getBytes());
                    put("f",          "".getBytes());
                    put("%^asdf",     "".getBytes());
                }}
            },
            { "   \"   =  ", new HashMap<String, Object>()},
            {
                "measure.thing.p99=2ms",
                new HashMap<String, Object>(){{put("measure.thing.p99", "2ms".getBytes());}}
            }
        };
    }

    @Test(dataProvider = "linesAsMap")
    public void nameValuePairsShouldParseAsAMap(String line, Map<String, Object> expected) {
        Map<String, Object> parsed = Logfmt.parse(line.getBytes());
        assertEquals(parsed, expected);
    }

}
