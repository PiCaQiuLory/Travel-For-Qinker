package com.ss.pojo.vo;

/**
 * Created by stella on 2016/7/15.
 */
public class Message {

    public static final int CODE_SUCCESS = 0;

    public static final int CODE_ERROR = 1;

    public static final int CODE_NOVALUE = 2;

    private int code;

    private Object value;

    public Message(){}

    public Message(int code, Object value){
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
