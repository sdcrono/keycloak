package com.onboard.demo;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("user")) {
            return User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("user123")
                    .roles("ROLE_USER")
                    .build();
        } else {
            return null;
        }
    }
}
