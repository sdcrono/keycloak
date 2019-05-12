package com.onboard.demo.security.config;

import com.onboard.demo.security.JwtConfigurer;
import com.onboard.demo.security.JwtParser;
import com.onboard.demo.security.KeycloakAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

//@Configuration
//@EnableWebSecurity
public class SecurityKeyCloakConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private KeycloakAuthenticationProvider keycloakAuthenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtParser jwtParser;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(keycloakAuthenticationProvider);
        auth.parentAuthenticationManager(authenticationManagerBean())
                .userDetailsService(userDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources")
                .hasAnyRole()
                .anyRequest().authenticated()
                .and()
                .oauth2Client()
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter());
    }

    private JwtConfigurer securityConfigurerAdapter() {
        return new JwtConfigurer(jwtParser);
    }
}
