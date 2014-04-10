package com.hedatou.wedding.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StdJson {

    private final int code;
    private final String msg;
    @JsonInclude(Include.NON_NULL)
    private final Object data;

    private StdJson(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    private static final StdJson OK = new StdJson(0, "OK", null);

    public static StdJson ok() {
        return OK;
    }

    public static StdJson ok(Object data) {
        return new StdJson(0, "OK", data);
    }

    public static StdJson err(String msg) {
        return new StdJson(1, msg, null);
    }

}
