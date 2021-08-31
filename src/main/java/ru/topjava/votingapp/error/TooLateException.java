package ru.topjava.votingapp.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class TooLateException extends AppException {
    public TooLateException(String msg) {
        super(HttpStatus.NOT_ACCEPTABLE,
                msg,
                ErrorAttributeOptions.of(MESSAGE));
    }
}
