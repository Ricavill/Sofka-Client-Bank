package com.example.sofkatransaction.shared.services.ms1;

import com.example.sofkatransaction.client.Client;
import com.example.sofkatransaction.shared.config.exceptions.FunctionalException;
import com.example.sofkatransaction.shared.config.security.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MicroService1RestAPI {
    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Autowired
    MicroService1RestAPI(RestTemplateBuilder restTemplateBuilder,
                         AuthService authService,
                         @Value("${MS1_URL}") String url) {
        this.authService = authService;
        this.restTemplate = restTemplateBuilder.rootUri(url).build();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public Client getClientById(Long clientId) {
        HttpHeaders headers = createHeaders();

        HttpEntity<Map> entity = new HttpEntity<>(new HashMap(), headers);
        ResponseEntity<Client> response = restTemplate
                .exchange("/clientes/" + clientId, HttpMethod.GET, entity, Client.class);
        try {
            return response.getBody();
        } catch (Exception e) {
            throw new FunctionalException("Error getting client by id: " + clientId, e);
        }
    }

}
