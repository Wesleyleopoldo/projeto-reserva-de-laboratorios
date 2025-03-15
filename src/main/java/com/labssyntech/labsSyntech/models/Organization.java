package com.labssyntech.labsSyntech.models;

import jakarta.persistence.*;

import java.util.UUID;

/*
 * Entidade da tabela de organizações no banco de dados...
 * Atributos...
 * Getters e Setters...
 */

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    public Organization(){
    }

    public Organization(UUID organizationId, String organizationName){
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

    public Organization(String name) {
        this.organizationName = name;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
