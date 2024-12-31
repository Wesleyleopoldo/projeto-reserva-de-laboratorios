package com.labssyntech.labsSyntech.models;

import jakarta.persistence.*;

import java.util.UUID;

/*
 * Entidade da tabela de Usuários no banco de dados...
 * Atributos...
 * Getters e Setters...
 */

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "is_president", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isPresident;

    @ManyToOne
    @JoinColumn(name = "fk_organization_id", referencedColumnName = "organization_id", nullable = true)
    private Organization organization;

    public User() {
    }

    public User(UUID userId, String userName, String email, String password, boolean isPresident, Organization organization) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isPresident = isPresident;
        this.organization = organization;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPresident() {
        return isPresident;
    }

    public void setPresident(boolean president) {
        isPresident = president;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
