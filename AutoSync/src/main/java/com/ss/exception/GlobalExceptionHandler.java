package com.ss.exception;

import com.ss.code.ErrorCode;
import com.ss.pojo.app.AppResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonResultException.class)
    @ResponseBody
    public AppResult commonsErrorHandler(CommonResultException cre){
        ErrorCode code = cre.getErrorCode();
        AppResult result = new AppResult(code.getCode(), code.name(), code.getMessage());
        return result;
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public AppResult defaultErrorHandler(CommonResultException cre){
        ErrorCode code = cre.getErrorCode();
        AppResult result = new AppResult(code.getCode(), code.name(), code.getMessage());
        return result;
    }

}
