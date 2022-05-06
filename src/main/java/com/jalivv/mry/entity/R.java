package com.jalivv.mry.entity;

public class R {

    public static final int SUCCES = 200;
    public static final int ERROR = 500;


    private int code;
    private String message;
    private Object data;

    public R() {
    }

    public R(int code, String message, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static R ok( Object data) {
        return new R(SUCCES,"success",  data);
    }

    public static R error(String message,Object data) {
        return new R(ERROR,message,  data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
