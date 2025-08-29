package com.sofka.sofkaClient.client;

import com.sofka.sofkaClient.shared.commons.PasswordUtils;
import com.sofka.sofkaClient.shared.config.exceptions.EntityNotFoundException;
import com.sofka.sofkaClient.shared.config.exceptions.ValidationException;
import com.sofka.sofkaClient.shared.config.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientService {

    public final ClientRepository clientRepository;
    public final SecurityProperties securityProperties;

    public ClientService(ClientRepository clientRepository, SecurityProperties securityProperties) {
        this.clientRepository = clientRepository;
        this.securityProperties = securityProperties;
    }

    public Client getClientById(Long id) {
        return this.clientRepository.findClientById(id).orElseThrow(() -> new EntityNotFoundException(Client.class, Map.of("id", id)));
    }

    public Client getClientByNameAndPassword(ClientRequest clientRequest) {
        return this.clientRepository.findClientByUsername(clientRequest.getUsername())
                .orElse(null);
    }

    public Client createClient(ClientRequest clientRequest) {
        Client client = this.getClientByNameAndPassword(clientRequest);
        if (client != null) {
            throw new ValidationException(HttpStatus.CONFLICT, "Validation Exception", "Client already exists.");
        }
        String hashedPassword = PasswordUtils.hash(clientRequest.getPassword(), securityProperties.getBcryptStrength());
        client = new Client(clientRequest);
        client.setPassword(hashedPassword);
        clientRepository.save(client);
        return client;
    }

    public Client editClient(Long clientId, ClientRequest clientRequest) {
        Client client = this.getClientById(clientId);
        client.update(clientRequest);
        clientRepository.save(client);
        return client;
    }


}
