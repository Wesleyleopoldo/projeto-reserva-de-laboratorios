package com.labssyntech.labsSyntech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "fk_organization_id", referencedColumnName = "organization_id", nullable = false)
    private Organization organizationId;

    @ManyToOne
    @JoinColumn(name = "fk_available_hours_id", referencedColumnName = "available_hours_id", nullable = false)
    private AvailableHours fkAvailableHours;
    
    @ManyToOne
    @JoinColumn(name = "fk_room_id", referencedColumnName = "room_id", nullable = false)
    private Room fkRoom;

    public Availability() {
    }

    public Availability(Long availabilityId, AvailableHours fkAvailableHours, Room fkRoom) {
        this.availabilityId = availabilityId;
        this.fkAvailableHours = fkAvailableHours;
        this.fkRoom = fkRoom;
    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    public AvailableHours getFkAvailableHours() {
        return fkAvailableHours;
    }

    public void setFkAvailableHours(AvailableHours fkAvailableHours) {
        this.fkAvailableHours = fkAvailableHours;
    }

    public Room getFkRoom() {
        return fkRoom;
    }

    public void setFkRoom(Room fkRoom) {
        this.fkRoom = fkRoom;
    }
}