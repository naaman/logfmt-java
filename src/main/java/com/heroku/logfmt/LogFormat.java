package com.heroku.logfmt;

import java.util.List;
import java.util.Map;

public interface LogFormat {
    public Map<String, Object> parse(byte[] line);
}
