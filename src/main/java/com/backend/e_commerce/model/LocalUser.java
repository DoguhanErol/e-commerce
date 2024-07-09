package com.backend.e_commerce.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "local_user")
public class LocalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password",nullable = false, length = 1000)
    private String password;
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;
    @Column(name = "first_name",nullable = false)
    private  String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Getter
    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    //Setter
    public void setId(){
        this.id = id;
    }
    public void  setUsername(){
        this.username = username;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
