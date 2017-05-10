package com.mars.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by tachen on 5/10/2017.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger =
        LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);


    @ExceptionHandler(value = { MarsException.class })
    protected ResponseEntity<Object> handleException(MarsException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        HttpStatus errorCode = ex.getServerErrorCode();
        return handleExceptionInternal(ex, bodyOfResponse,
            new HttpHeaders(), errorCode, request);
    }
}