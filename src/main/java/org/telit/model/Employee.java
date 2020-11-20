package org.telit.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.telit.api.util.BCryptSensistiveDataDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@MappedSuperclass
public class Employee {

    @Id @GeneratedValue private Long id;
    private String name;
    private String lastName;
    
    @Column(name="password", length = 60) //BCrypted data will be always 60char
    @JsonDeserialize(using = BCryptSensistiveDataDeserializer.class)
    private String password;
    private String address;
    private String type;
    private Boolean active;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
