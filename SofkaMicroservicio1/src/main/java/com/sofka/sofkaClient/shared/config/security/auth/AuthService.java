package com.sofka.sofkaClient.shared.config.security.auth;

import com.sofka.sofkaClient.client.Client;
import com.sofka.sofkaClient.shared.commons.ObjectMapperUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {


    public AuthService(TokenAuthenticationService tokenAuthenticationService) {
    }

    public Client getAuthenticatedClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() != null) {
            Map map= (Map) auth.getDetails();
            return ObjectMapperUtils.mapTo(Map.of("id",map.get("id")), Client.class);
        }
        return null;
    }
}
