package com.gov.pe.projeto.labreservas.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @Column(name = "name", nullable = false)
    private String name;

    public Room(String name) {
        this.name = name;
    }
}
