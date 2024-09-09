package com.gov.pe.projeto.labreservas.services;

import com.gov.pe.projeto.labreservas.dtos.RoomDTO;
import com.gov.pe.projeto.labreservas.model.Room;
import com.gov.pe.projeto.labreservas.payloads.RoomRequestPayload;
import com.gov.pe.projeto.labreservas.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository repository;

    public void cadastrarSala(RoomRequestPayload payload) {
        Room newRoom = new Room(payload.name());
        repository.save(newRoom);
        System.out.println(newRoom.getRoom_id());
    }

    public List<RoomDTO> listarSalas(){

        List<Room> listaDeSalas = new ArrayList<>(this.repository.findAll());

        return listaDeSalas.stream().map(this::converterParaDTO).toList();
    }

    private RoomDTO converterParaDTO(Room room) {
        return RoomDTO.builder().name(room.getName()).id(room.getRoom_id()).build();
    }
}
