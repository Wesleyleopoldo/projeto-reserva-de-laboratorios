package com.gov.pe.projeto.labreservas.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository repository;

    @PostMapping
    public ResponseEntity<CreateResponseRoom> createRoom(@RequestBody RoomRequestPayload payload){
        System.out.println("Recebendo Requisição do tipo POST...");

        Room newRoom = new Room(payload.name());

        this.repository.save(newRoom);

        return ResponseEntity.ok(new CreateResponseRoom(newRoom.getRoom_id()));
    }
}
