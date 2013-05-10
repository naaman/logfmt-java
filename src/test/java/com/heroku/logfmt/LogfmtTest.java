package com.heroku.logfmt;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class LogfmtTest {

    @DataProvider
    public Object[][] linesAsMap() {
        return new Object[][]{
            {
                "   a=foo bar=10ms E=\"12\\\"3\" d foo=",
                new HashMap<String, Object>(){{
                    put("a",   "foo".toCharArray());
                    put("bar", "10ms".toCharArray());
                    put("E",   "12\\\"3".toCharArray());
                    put("d",   "".toCharArray());
                    put("foo", "".toCharArray());
                }}
            },
            {
                "foo=bar a=1\\4 baz=\"hello kitty\" cool%story=bro f %^asdf  ",
                new HashMap<String, Object>(){{
                    put("foo",        "bar".toCharArray());
                    put("a",          "1\\4".toCharArray());
                    put("baz",        "hello kitty".toCharArray());
                    put("cool%story", "bro".toCharArray());
                    put("f",          "".toCharArray());
                    put("%^asdf",     "".toCharArray());
                }}
            },
            { "   \"   =  ", new HashMap<String, Object>()},
            {
                "measure.thing.p99=2ms",
                new HashMap<String, char[]>(){{put("measure.thing.p99", "2ms".toCharArray());}}
            },
            { "ƒ=func", new HashMap<String, char[]>(){{put("ƒ", "func".toCharArray());}} }
        };
    }

    @Test(dataProvider = "linesAsMap")
    public void nameValuePairsShouldParseAsAMap(String line, Map<String, Object> expected) {
        Map<String, char[]> parsed = Logfmt.parse(line.toCharArray());
        assertEquals(parsed, expected);
    }

}
