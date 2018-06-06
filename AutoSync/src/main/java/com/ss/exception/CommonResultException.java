package com.ss.exception;

import com.ss.code.ErrorCode;

public class CommonResultException extends RuntimeException{

	private ErrorCode errorCode;

    private Integer code;
    private String errorMessage;
	
    public CommonResultException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
