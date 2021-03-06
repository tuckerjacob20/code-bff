package com.e451.rest.services.impl;

import com.e451.rest.domains.user.ResetForgottenPasswordRequest;
import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.domains.user.UserVerification;
import com.e451.rest.gateways.UserServiceGateway;
import com.e451.rest.repository.UserRepository;
import com.e451.rest.services.AccountLockoutService;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserServiceGateway userServiceGateway;
    private final UserRepository userRepository;
    private final AccountLockoutService accountLockoutService;

    @Autowired
    public UserServiceImpl(
            UserServiceGateway userServiceGateway,
            UserRepository userRepository,
            AccountLockoutService accountLockoutService) {
        this.userServiceGateway = userServiceGateway;
        this.userRepository = userRepository;
        this.accountLockoutService = accountLockoutService;
    }

    @Override
    public ResponseEntity<UserResponse> getUsers() {
        return userServiceGateway.getUsers();
    }

    @Override
    public ResponseEntity<UserResponse> getUsers(int page, int size, String property) {
        return userServiceGateway.getUsers(page, size, property);
    }

    @Override
    public ResponseEntity<UserResponse> searchUsers(int page, int size, String property, String searchString) {
        return userServiceGateway.searchUsers(page, size, property, searchString);
    }

    @Override
    public ResponseEntity<UserResponse> createUser(User user) {
        return userServiceGateway.createUser(user);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(User user) { return userServiceGateway.updateUser(user); }

    @Override
    public ResponseEntity<UserResponse> unlockUser(User user) { return userServiceGateway.unlockUser(user); }

    @Override
    public ResponseEntity<UserResponse> updateUser(UserVerification userVerification) {
        return userServiceGateway.updateUser(userVerification);
    }
    public ResponseEntity deleteUser(String id) { return userServiceGateway.deleteUser(id); }

    @Override
    public ResponseEntity activate(String guid) {
        return userServiceGateway.activate(guid);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username %s" ,username));
        }

        accountLockoutService.canAccountLogin(user);

        return user;
    }

    @Override
    public ResponseEntity<UserResponse> getActiveUser() { return userServiceGateway.getActiveUser(); }

    @Override
    public ResponseEntity forgotPassword(String username) {
        return userServiceGateway.forgotPassword(username);
    }

    @Override
    public ResponseEntity resetForgottenPassword(ResetForgottenPasswordRequest request) {
        return userServiceGateway.resetForgottenPassword(request);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
