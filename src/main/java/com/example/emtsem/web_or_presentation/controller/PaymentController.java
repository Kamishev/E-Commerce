package com.example.emtsem.web_or_presentation.controller;

import com.example.emtsem.model.Shoe;
import com.example.emtsem.model.ShoppingCart;
import com.example.emtsem.model.dto.ChargeRequest;
import com.example.emtsem.service_or_business.AuthService;
import com.example.emtsem.service_or_business.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public PaymentController(ShoppingCartService shoppingCartService,
                             AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @GetMapping("/charge")
    public String getCheckoutPage(Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "eur");
            model.addAttribute("amount", (int) (shoppingCart.getShoes().stream().mapToDouble(Shoe::getPrice).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        } catch (RuntimeException ex) {
            return "redirect:/shoes?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest, Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/shoes?message=Successful Payment";
        } catch (RuntimeException ex) {
            return "redirect:/payments/charge?error=" + ex.getLocalizedMessage();
        }
    }
}
