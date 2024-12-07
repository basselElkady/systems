package com.example.systems.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class InvoiceNotFound extends RuntimeException {

    public InvoiceNotFound(String message) {
        super(message);
    }
}
