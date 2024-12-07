package com.example.systems.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class NotFoundCompany  extends RuntimeException {

    public NotFoundCompany(String message) {
        super(message);
    }

}
