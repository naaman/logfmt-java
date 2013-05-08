package com.heroku.logfmt;

import java.util.Map;

public interface LogFormat {
    public Map<String, Object> parse(byte[] line);
}
