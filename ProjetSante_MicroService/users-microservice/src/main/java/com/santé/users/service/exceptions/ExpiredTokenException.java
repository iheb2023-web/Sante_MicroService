package com.santé.users.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExpiredTokenException extends RuntimeException {
    private String expiredToken;
    public ExpiredTokenException(String expiredToken) {
        super(expiredToken);

    }
}
