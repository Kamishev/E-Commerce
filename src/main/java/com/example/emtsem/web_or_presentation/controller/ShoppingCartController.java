package com.example.emtsem.web_or_presentation.controller;

import com.example.emtsem.model.ShoppingCart;
import com.example.emtsem.service_or_business.AuthService;
import com.example.emtsem.service_or_business.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @PostMapping("/{shoeId}/add-shoe")
    public String addShoeToShoppingCart(@PathVariable Long shoeId) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addShoeToShoppingCart(this.authService.getCurrentUserId(), shoeId);
        } catch (RuntimeException ex) {
            return "redirect:/shoes?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/shoes";
    }


    @PostMapping("/{shoeId}/remove-shoe")
    public String removeShoeToShoppingCart(@PathVariable Long shoeId) {
        ShoppingCart shoppingCart = this.shoppingCartService.removeShoeFromShoppingCart(this.authService.getCurrentUserId(), shoeId);
        return "redirect:/shoes";
    }
}
