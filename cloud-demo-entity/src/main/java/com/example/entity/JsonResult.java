package com.example.entity;


public class JsonResult {

    private int code;
    private String msg;
    private Object date;

    public JsonResult(int code, String msg, Object date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult() {
    }

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

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
