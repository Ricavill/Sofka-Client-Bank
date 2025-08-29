package com.example.sofkatransaction.shared.config.security.auth;

import com.example.sofkatransaction.client.Client;
import com.example.sofkatransaction.shared.commons.ObjectMapperUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    public AuthService() {
    }

    public Client getAuthenticatedClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            Jwt jwt = ((Jwt) auth.getPrincipal());
            Map<String, Object> claims = jwt.getClaims();
            return ObjectMapperUtils.mapTo(claims, Client.class);
        }
        return null;
    }

    public String getAuthToken(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            Jwt jwt = ((Jwt) auth.getPrincipal());
            return jwt.getTokenValue();
        }
        return null;
    }


}