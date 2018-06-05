package com.example.MicServices_2.domain;

public class QueryResult {
    private int code;
    private String msg;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    public String toString() {
        return "QueryResult [code=" + code + ", msg=" + msg + "]";
    }
}
