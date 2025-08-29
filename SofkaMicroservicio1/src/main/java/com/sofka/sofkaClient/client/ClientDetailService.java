package com.sofka.sofkaClient.client;

import com.sofka.sofkaClient.shared.config.exceptions.EntityNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientDetailService implements UserDetailsService {
    private final ClientRepository clientRepository;

    public ClientDetailService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(Client.class, Map.of("username", username)));
        return User.withUsername(client.getUsername()).password(client.getPassword()).authorities("ROLE_CLIENT").build();
    }
}
