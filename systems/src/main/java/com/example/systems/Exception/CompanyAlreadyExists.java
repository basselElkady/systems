package com.example.systems.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.CONFLICT)
public class CompanyAlreadyExists extends RuntimeException{

    public CompanyAlreadyExists(String message) {
        super(message);
    }

}
