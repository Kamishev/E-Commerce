package com.example.emtsem.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShoeNotFoundException extends RuntimeException {
    public ShoeNotFoundException(Long id) {
        super(String.format("Shoe with %d was not found!", id));
    }
}
