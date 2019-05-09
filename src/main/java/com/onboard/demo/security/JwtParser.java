package com.onboard.demo.security;

import com.onboard.demo.security.config.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

//@Component
public class JwtParser {

    @Autowired
    private SecurityProperties securityProperties;

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private long tokenValidityInMilliseconds;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(
                securityProperties.getAuthentication().getJwt().getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds =
                1000 * securityProperties.getAuthentication().getJwt().getTokenValidityInSeconds();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
