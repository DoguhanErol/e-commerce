package com.backend.e_commerce.model;

import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();

    @OrderBy("id desc")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @JsonIgnore
    @Column(name = "password",nullable = false, length = 1000)
    private String password;
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;
    @Column(name = "first_name",nullable = false)
    private  String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email_verified", nullable = false)
    private Boolean isEmailVerified = false;




    //Getter
    public Long getId(){
        return id;
    }
    public String getPassword() {
        return password;
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
    public List<VerificationToken> getVerificationTokens() {
        return verificationTokens;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    //Setter
    public void setId(){
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void  setUsername(String username){
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

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public void setVerificationTokens(List<VerificationToken> verificationTokens) {
        this.verificationTokens = verificationTokens;
    }
}
