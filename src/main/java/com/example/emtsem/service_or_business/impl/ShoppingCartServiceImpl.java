package com.example.emtsem.service_or_business.impl;

import com.example.emtsem.model.Shoe;
import com.example.emtsem.model.ShoppingCart;
import com.example.emtsem.model.User;
import com.example.emtsem.model.dto.ChargeRequest;
import com.example.emtsem.model.enumerations.CartStatus;
import com.example.emtsem.model.exception.*;
import com.example.emtsem.persistence_or_repository.ShoppingCartRepository;
import com.example.emtsem.service_or_business.PaymentService;
import com.example.emtsem.service_or_business.ShoeService;
import com.example.emtsem.service_or_business.ShoppingCartService;
import com.example.emtsem.service_or_business.UserService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final ShoeService shoeService;
    private final PaymentService paymentService;

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(UserService userService,
                                   ShoeService shoeService,
                                   PaymentService paymentService,
                                   ShoppingCartRepository shoppingCartRepository) {
        this.userService = userService;
        this.shoeService = shoeService;
        this.paymentService = paymentService;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return this.shoppingCartRepository.findAllByUserUsername(userId);
    }

    @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if (this.shoppingCartRepository.existsByUserUsernameAndStatus(
                user.getUsername(),
                CartStatus.CREATED
        )) {
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addShoeToShoppingCart(String userId, Long shoeId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        Shoe shoe = this.shoeService.findById(shoeId);
        for (Shoe s : shoppingCart.getShoes()) {
            if (s.getId().equals(shoeId)) {
                throw new ShoeIsAlreadyInShoppingCartException(shoe.getName());
            }
        }
        shoppingCart.getShoes().add(shoe);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeShoeFromShoppingCart(String userId, Long shoeId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setShoes(
                shoppingCart.getShoes()
                        .stream()
                        .filter(shoe -> !shoe.getId().equals(shoeId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
        shoppingCart.setStatus(CartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));

        List<Shoe> shoes = shoppingCart.getShoes();
        float price = 0;

        for (Shoe shoe : shoes) {
            if (shoe.getQuantity() <= 0) {
                throw new ShoeOutOfStockException(shoe.getName());
            }
            shoe.setQuantity(shoe.getQuantity() - 1);
            price += shoe.getPrice();
        }
        Charge charge = new Charge();
        try {
            charge = this.paymentService.pay(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
            throw new TransactionFailedException(userId, e.getMessage());
        }

        shoppingCart.setShoes(shoes);
        shoppingCart.setStatus(CartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

}
