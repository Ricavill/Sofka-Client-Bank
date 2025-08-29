package com.sofka.sofkaClient.client;

import com.sofka.sofkaClient.person.PersonGender;
import com.sofka.sofkaClient.shared.commons.Status;
import jakarta.validation.constraints.NotNull;

//Se crea request aparte para temas sensibles como password y para datos que no se usan como status
public class ClientRequest {
    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private PersonGender gender;

    @NotNull
    private String password;

    @NotNull
    private int age;

    @NotNull
    private String identification;

    @NotNull
    private String telephone;

    @NotNull
    private String address;

    private String hashedPassword;

    private Status status;


    public ClientRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PersonGender getGender() {
        return gender;
    }

    public void setGender(PersonGender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
