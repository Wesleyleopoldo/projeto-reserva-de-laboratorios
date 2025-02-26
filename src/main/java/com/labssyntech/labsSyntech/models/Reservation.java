package com.labssyntech.labsSyntech.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * Entidade da tabela de reservas no banco de dados...
 * Atributos...
 * Getters e Setters...
 */

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
    @JoinColumn(name = "fk_room_id", referencedColumnName = "room_id", nullable = false)
    private Room roomId;

    @OneToOne
    @JoinColumn(name = "fk_availability_id", referencedColumnName = "availability_id", nullable = false)
    private Availability availabilityId;

    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateTheReservation;

    public Reservation() {
    }

    public Reservation(UUID reservationId, User userId, Organization organizationId, LocalDateTime dateTheReservation) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.organizationId = organizationId;
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

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public Availability getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Availability availabilityId) {
        this.availabilityId = availabilityId;
    }

    public LocalDateTime getDateTheReservation() {
        return dateTheReservation;
    }

    public void setDateTheReservation(LocalDateTime dateTheReservation) {
        this.dateTheReservation = dateTheReservation;
    }
}
