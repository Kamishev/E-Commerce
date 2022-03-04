package com.example.emtsem.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ShoeIsAlreadyInShoppingCartException extends RuntimeException {
    public ShoeIsAlreadyInShoppingCartException(String shoeName) {
        super(String.format("Shoe %s is alraedy in active shopping cart", shoeName));
    }
}
