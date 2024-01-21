package com.checkout.service;

import com.checkout.model.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private JWTService jwtService;
    private UserDetailsService userDetailsService;

    public UserService(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public String loginUser(LoginUser loginUser) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUserName());

        if (userDetails != null) {
            if (loginUser.getPassword().equals(userDetails.getPassword())) {
                return jwtService.generateJWT(loginUser);
            }
        }
        return null;
    }
}