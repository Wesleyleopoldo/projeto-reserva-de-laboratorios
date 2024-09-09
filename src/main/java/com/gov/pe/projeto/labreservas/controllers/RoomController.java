package com.gov.pe.projeto.labreservas.controllers;

import com.gov.pe.projeto.labreservas.dtos.RoomDTO;
import com.gov.pe.projeto.labreservas.model.Room;
import com.gov.pe.projeto.labreservas.payloads.RoomRequestPayload;
import com.gov.pe.projeto.labreservas.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gov.pe.projeto.labreservas.repository.RoomRepository;

import java.util.List;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    @Autowired
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody RoomRequestPayload payload){
        System.out.println("Recebendo Requisição do tipo POST...");

        this.roomService.cadastrarSala(payload);

        return ResponseEntity.ok("Salvo com sucesso!!!");
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> listarSalas(){
        return ResponseEntity.ok(this.roomService.listarSalas());
    }
}
