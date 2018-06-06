package com.ss.code;

public enum ErrorCode {

    ERROR_PARAM(400,"参数为空"),
    ERROR_SYSTEM(500,"系统异常"),
    ERROR_PERMISSION(403,"授权失败，禁止访问");

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
