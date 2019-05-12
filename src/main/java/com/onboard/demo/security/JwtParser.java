package com.onboard.demo.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtParser {

    private ConfigurableJWTProcessor jwtProcessor;

    @PostConstruct
    public void init() throws IOException {
       JWKSource keySource = new RemoteJWKSet(new URL("https://auth.dev.kegmil.co/auth/realms/onboarding/protocol/openid-connect/certs"));
        JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
        JWSKeySelector keySelector = new JWSVerificationKeySelector(expectedJWSAlg, keySource);

        this.jwtProcessor = new DefaultJWTProcessor();

        jwtProcessor.setJWSKeySelector(keySelector);
    }

    public Authentication getAuthentication(String token) {

        SecurityContext ctx = null;
        JWTClaimsSet claimsSet = null;
        try {
            claimsSet = jwtProcessor.process(token, ctx);
        } catch (ParseException e) {
            throw new BadRequestException();
        } catch (BadJOSEException e) {
            throw new BadRequestException();
        } catch (JOSEException e) {
            throw new BadRequestException();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(claimsSet.getClaim("realm_access").toString());
        } catch (IOException e) {
            throw new BadRequestException();
        }
        JsonNode rolesNode = root.path("roles");
        List<String> roles = new ArrayList<>();
        if (rolesNode.isArray()) {
            for (final JsonNode role : rolesNode) {
                roles.add(role.asText());
            }
        }

        Collection<? extends GrantedAuthority> authorities =
                roles
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claimsSet.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
