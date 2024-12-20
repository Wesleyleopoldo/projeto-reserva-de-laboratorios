package com.labssyntech.labsSyntech.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "fk_organization_id", referencedColumnName = "organization_id", nullable = false)
    private Organization organizationId;

    @ManyToOne
    @JoinColumn(name = "fk_days_of_week", referencedColumnName = "days_in_the_week_id", nullable = false)
    private DaysInTheWeek daysOfWeek;

    @ManyToOne
    @JoinColumn(name = "fk_avaulable_hours", referencedColumnName = "available_hours_id", nullable = false)
    private AvailableHours hoursId;

    @CreationTimestamp
    @Column(name = "date_reservation", nullable = false, updatable = false)
    private LocalDateTime dateTheReservation;

    public Reservation() {
    }

    public Reservation(UUID reservationId, User userId, Organization organizationId, DaysInTheWeek daysOfWeek, AvailableHours hoursId, LocalDateTime dateTheReservation) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.organizationId = organizationId;
        this.daysOfWeek = daysOfWeek;
        this.hoursId = hoursId;
        this.dateTheReservation = dateTheReservation;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    public DaysInTheWeek getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(DaysInTheWeek daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public AvailableHours getHoursId() {
        return hoursId;
    }

    public void setHoursId(AvailableHours hoursId) {
        this.hoursId = hoursId;
    }

    public LocalDateTime getDateTheReservation() {
        return dateTheReservation;
    }

    public void setDateTheReservation(LocalDateTime dateTheReservation) {
        this.dateTheReservation = dateTheReservation;
    }
}
