package com.sofka.sofkaClient.shared.config.security.auth;

import com.sofka.sofkaClient.client.Client;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenAuthenticationService tokenAuthenticationService;

    public AuthService(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    public Client getAuthenticatedClient() {
        return this.tokenAuthenticationService.getClient();
    }
}
