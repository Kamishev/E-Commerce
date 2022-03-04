package com.example.emtsem.service_or_business;

import com.example.emtsem.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword);
}
