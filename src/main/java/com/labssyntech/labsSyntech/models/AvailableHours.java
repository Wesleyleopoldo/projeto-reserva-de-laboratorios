package com.labssyntech.labsSyntech.models;

import jakarta.persistence.*;

import java.time.LocalTime;

/*
 * Entidade da tabela de hórarios disponiveis no banco de dados...
 * Atributos...
 * Getters e Setters...
 */

@Entity
@Table(name = "available_hours")
public class AvailableHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_hours_id", nullable = false)
    private Long availableHoursId;

    @Column(name = "hours", nullable = false)
    private LocalTime hours;

    @ManyToOne
    @JoinColumn(name = "fk_organization_id", referencedColumnName = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "fk_days_in_the_week_id", referencedColumnName = "days_in_the_week_id", nullable = false)
    private DaysInTheWeek daysInTheWeek;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_room_id", referencedColumnName = "room_id", nullable = false)
    private Room roomId;

    public AvailableHours() {
    }

    public AvailableHours(Long availableHours, LocalTime hours, DaysInTheWeek daysInTheWeek, Room roomId) {
        this.availableHoursId = availableHours;
        this.hours = hours;
        this.daysInTheWeek = daysInTheWeek;
        this.roomId = roomId;
    }

    public DaysInTheWeek getDaysInTheWeek() {
        return daysInTheWeek;
    }

    public void setDaysInTheWeek(DaysInTheWeek daysInTheWeek) {
        this.daysInTheWeek = daysInTheWeek;
    }

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }
    
    public Long getAvailableHoursId() {
        return availableHoursId;
    }

    public void setAvailableHoursId(Long availableHours) {
        this.availableHoursId = availableHours;
    }
    
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }
    
}
