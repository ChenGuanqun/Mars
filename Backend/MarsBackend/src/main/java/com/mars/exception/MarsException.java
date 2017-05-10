package com.mars.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by tachen on 5/10/2017.
 */
public class MarsException extends Exception {

    private HttpStatus serverErrorCode;


    public MarsException() {
    }

    public MarsException(String message) {
        super(message);
    }

    public MarsException(HttpStatus serverErrorCode, String message) {
        super(message);
        this.serverErrorCode = serverErrorCode;
    }


    public HttpStatus getServerErrorCode() {
        return serverErrorCode;
    }

    public void setServerErrorCode(HttpStatus serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
    }
}
