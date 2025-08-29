package com.sofka.sofkaClient.client;

import com.sofka.sofkaClient.SofkaTestApplication;
import com.sofka.sofkaClient.person.PersonGender;
import com.sofka.sofkaClient.shared.commons.PasswordUtils;
import com.sofka.sofkaClient.shared.commons.Status;
import com.sofka.sofkaClient.shared.config.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class ClientServiceIntegrationTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;


    @Test
    void createClient_persistsAndHashesPassword() {
        ClientRequest req = new ClientRequest();
        req.setUsername("new_user");
        req.setPassword("plain123");
        req.setIdentification("0926736843");
        req.setStatus(Status.ACTIVE);
        req.setName("Ricardo");
        req.setAge(28);
        req.setGender(PersonGender.MALE);
        req.setAddress("address");
        req.setTelephone("099");


        Client created = clientService.createClient(req);

        Client fromDb = clientRepository.findById(created.getId()).orElseThrow();
        assertEquals("new_user", fromDb.getUsername());
        assertTrue(PasswordUtils.matches("plain123", fromDb.getPassword()));
    }

    @Test
    void createClient_conflictWhenUserExists() {
        // Sembrado programático
        Client existing = new Client();
        existing.setUsername("ric");
        existing.setPassword(PasswordUtils.hash("hola123", 10));
        existing.setStatus(Status.ACTIVE);
        existing.setName("Ricardo");
        existing.setAge(28);
        existing.setGender(PersonGender.MALE);
        existing.setAddress("address");
        existing.setTelephone("099");
        existing.setIdentification("092");
        clientRepository.save(existing);

        ClientRequest req = new ClientRequest();
        req.setUsername("ric");
        req.setPassword("anything");
        existing.setStatus(Status.ACTIVE);
        existing.setName("Ricardo2");
        existing.setAge(27);
        existing.setGender(PersonGender.FEMALE);
        existing.setAddress("address2");
        existing.setTelephone("0992");
        existing.setIdentification("0923");
        assertThrows(ValidationException.class, () -> clientService.createClient(req));
    }
}
