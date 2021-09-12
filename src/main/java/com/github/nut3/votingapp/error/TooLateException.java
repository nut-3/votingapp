package com.github.nut3.votingapp.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class TooLateException extends AppException {
    public TooLateException(String msg) {
        super(HttpStatus.UNPROCESSABLE_ENTITY,
                msg,
                ErrorAttributeOptions.of(MESSAGE));
    }
}
