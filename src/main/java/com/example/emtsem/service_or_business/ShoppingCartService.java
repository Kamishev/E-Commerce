package com.example.emtsem.service_or_business;

import com.example.emtsem.model.ShoppingCart;
import com.example.emtsem.model.dto.ChargeRequest;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart findActiveShoppingCartByUsername(String userId);

    List<ShoppingCart> findAllByUsername(String userId);

    ShoppingCart createNewShoppingCart(String userId);

    ShoppingCart addShoeToShoppingCart(String userId,
                                          Long shoeId);

    ShoppingCart removeShoeFromShoppingCart(String userId,
                                               Long shoeId);

    ShoppingCart getActiveShoppingCart(String userId);

    ShoppingCart cancelActiveShoppingCart(String userId);

    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest);

}
