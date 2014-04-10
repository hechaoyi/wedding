package com.hedatou.wedding.web.util;

public class StdJson {

    private final int code;
    private final String msg;

    private StdJson(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private static final StdJson OK = new StdJson(0, "OK");

    public static StdJson ok() {
        return OK;
    }

    public static StdJson err(String msg) {
        return new StdJson(1, msg);
    }

}
