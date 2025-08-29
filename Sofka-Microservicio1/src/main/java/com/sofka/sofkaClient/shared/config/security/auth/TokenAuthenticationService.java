package com.sofka.sofkaClient.shared.config.security.auth;

import com.sofka.sofkaClient.client.Client;
import com.sofka.sofkaClient.client.ClientRepository;
import com.sofka.sofkaClient.shared.config.exceptions.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenAuthenticationService {
    private final JWTService jwtService;
    private final ClientRepository clientRepository;

    public TokenAuthenticationService(JWTService jwtService, ClientRepository clientRepository) {
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
    }

    public String encodeToken(AuthenticationRequest authenticationRequest) {
        Client client = clientRepository.
                findClientByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Map<String, Object> data = Client.toUserData(client);
        return jwtService.generateToken(client.getUsername(), data);
    }

    Client getClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof Client ?
                (Client) auth.getPrincipal() :
                null;
    }

}
