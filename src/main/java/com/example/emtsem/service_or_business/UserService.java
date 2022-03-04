package com.example.emtsem.service_or_business;

import com.example.emtsem.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(String userId);
    User registerUser(User user);
}
