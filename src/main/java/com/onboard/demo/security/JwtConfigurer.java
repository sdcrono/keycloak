package com.onboard.demo.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtParser jwtParser;

    /**
     * Constructor of {@link JwtConfigurer}.
     *
     * @param jwtParser the given token provider
     */
    public JwtConfigurer(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(jwtParser);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
