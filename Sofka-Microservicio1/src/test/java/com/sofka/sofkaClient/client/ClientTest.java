package com.sofka.sofkaClient.client;

import com.sofka.sofkaClient.shared.commons.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ClientTest {

    private Client newClient(String username, String hashed) {
        ClientRequest request = new ClientRequest();
        request.setUsername(username);
        request.setHashedPassword(hashed);
        request.setStatus(Status.ACTIVE);
        Client c = new Client(request);
        return c;
    }

    @Test
    void update_ignoresReadOnly_username_and_password_but_updates_others() {
        // given
        Client c = newClient("maria", "$2a$10$oldhash");
        assertEquals(Status.ACTIVE, c.getStatus());

        // request que INTENTA cambiar username y password (read-only) y sí cambia status
        ClientRequest reqUpdate = new ClientRequest();
        reqUpdate.setUsername("nuevoUser");
        reqUpdate.setHashedPassword("$2a$10$oldhash");
        reqUpdate.setStatus(Status.DISABLED);


        // when
        c.update(reqUpdate);

        // then: username y password NO deben cambiar
        assertEquals("maria", c.getUsername(), "username es read-only y no debe cambiar en update()");
        assertEquals("$2a$10$oldhash", c.getPassword(), "password es read-only y no debe cambiar en update()");

        // y sí debe actualizar campos no read-only (ej. status)
        assertEquals(Status.DISABLED, c.getStatus(), "status debe actualizarse desde el request");
    }
}
