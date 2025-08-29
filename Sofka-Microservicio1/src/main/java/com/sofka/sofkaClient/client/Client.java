package com.sofka.sofkaClient.client;

import com.sofka.sofkaClient.person.Person;
import com.sofka.sofkaClient.shared.commons.PropertiesUtils;
import com.sofka.sofkaClient.shared.commons.Status;
import com.sofka.sofkaClient.shared.config.data.history.Memento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/* En el documento se pide que se herede de persona(es una entidad), esto por JPA le dara mismo id(PK) de
persona a cliente,pero ademas tambien piden que tenga clientId(no es claro en el documento si es un id propio o no),
rompiendo el principio de herencia y degradando las practicas, lo que es confuso. Debido a esto se aplicara
como metodo la herencia como se dijo en vez de composition.*/
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class Client extends Person implements Cloneable {
    private static final String[] READ_ONLY_PROPERTIES = {"username", "password"};

    //todo pensar si es buena idea poner unique
    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 60)
    @Column(name = "password_hash")
    private String password;

    //Usualmente se toma 0 como inactivo y 1 como activo, pero prefiero ser especifico.
    private Status status;

    public Client() {
    }

    public Client(ClientRequest clientRequest) {
        super(clientRequest);
        this.username = clientRequest.getUsername();
        this.password = clientRequest.getHashedPassword();
        this.status = Status.ACTIVE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static Map<String, Object> toUserData(Client client) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", client.getId());
        data.put("username", client.getUsername());
        data.put("identification", client.getIdentification());
        data.put("status", client.getStatus());
        return data;
    }

    public void update(ClientRequest request) {
        String[] nullProperties = PropertiesUtils.getNullPropertyNames(request);
        String[] ignoreProperties = Stream.of(nullProperties, READ_ONLY_PROPERTIES)
                .flatMap(Stream::of)
                .toArray(String[]::new);
        BeanUtils.copyProperties(request, this, ignoreProperties);
    }


    @Override
    public Client clone() {
        try {
            return (Client) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Memento<Client> makeSnapshot() {
        Client snapshot = clone();
        return new Memento<>(snapshot, this);
    }

}
