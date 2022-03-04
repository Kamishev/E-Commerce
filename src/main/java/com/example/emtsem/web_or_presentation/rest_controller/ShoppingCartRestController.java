package com.example.emtsem.web_or_presentation.rest_controller;

import com.example.emtsem.model.ShoppingCart;
import com.example.emtsem.service_or_business.AuthService;
import com.example.emtsem.service_or_business.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService,
                                      AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping
    public List<ShoppingCart> findAllByUsername() {
        return this.shoppingCartService.findAllByUsername(this.authService.getCurrentUserId());
    }

    @PostMapping
    public ShoppingCart createNewShoppingCart() {
        return this.shoppingCartService.createNewShoppingCart(this.authService.getCurrentUserId());
    }

    @PatchMapping("/{shoeId}/shoes")
    public ShoppingCart addShoeToCart(@PathVariable Long shoeId) {
        return this.shoppingCartService.addShoeToShoppingCart(
                this.authService.getCurrentUserId(),
                shoeId
        );
    }

    @DeleteMapping("/{shoeId}/shoes")
    public ShoppingCart removeShoeFromCart(@PathVariable Long shoeId) {
        return this.shoppingCartService.removeShoeFromShoppingCart(
                this.authService.getCurrentUserId(),
                shoeId
        );
    }

    @PatchMapping("/cancel")
    public ShoppingCart cancelActiveShoppingCart() {
        return this.shoppingCartService.cancelActiveShoppingCart(this.authService.getCurrentUserId());
    }

    @PatchMapping("/checkout")
    public ShoppingCart checkoutActiveShoppingCart() {

        return null;
    }



}
