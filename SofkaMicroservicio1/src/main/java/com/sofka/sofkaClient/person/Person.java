package com.sofka.sofkaClient.person;

import com.sofka.sofkaClient.auditing.AuditableEntity;
import com.sofka.sofkaClient.client.ClientRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Person extends AuditableEntity<Long> {
    @NotNull
    private String name;

    @NotNull
    private PersonGender gender;

    @NotNull
    private int age;

    @NotNull
    private String address;

    @NotNull
    private String identification;

    @NotNull
    private String telephone;

    public Person() {

    }

    public Person(ClientRequest clientRequest) {
        this.name = clientRequest.getName();
        this.address = clientRequest.getAddress();
        this.gender = clientRequest.getGender();
        this.age = clientRequest.getAge();
        this.identification = clientRequest.getIdentification();
        this.telephone = clientRequest.getTelephone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonGender getGender() {
        return gender;
    }

    public void setGender(PersonGender gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

}
