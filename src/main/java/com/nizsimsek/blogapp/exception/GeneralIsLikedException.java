package com.nizsimsek.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeneralIsLikedException extends RuntimeException {

    public GeneralIsLikedException(String message) {
        super(message);
    }
}
