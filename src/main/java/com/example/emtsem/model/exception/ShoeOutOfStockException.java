package com.example.emtsem.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ShoeOutOfStockException extends RuntimeException {
    public ShoeOutOfStockException(String name) {
        super(String.format("Shoe %s is out of stock!", name));
    }
}
