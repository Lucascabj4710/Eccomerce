package com.eccomerce.controller;

import com.eccomerce.persistence.dto.request.ClientDto;
import com.eccomerce.service.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/client")
public class ClientController {

    private final ClientServiceImpl clientServiceImple;

    public ClientController(ClientServiceImpl clientServiceImple) {
        this.clientServiceImple = clientServiceImple;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientDto clientDto){

        clientServiceImple.createClient(clientDto);

        return new ResponseEntity<>("COMPLETED", HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getClients(){

        return ResponseEntity.ok(clientServiceImple.getClients());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getClientID(@PathVariable Long id){

        return ResponseEntity.ok(clientServiceImple.getClientId(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClientID(@PathVariable Long id){

        return ResponseEntity.ok(clientServiceImple.deleteClient(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClientID(@PathVariable Long id, @RequestBody ClientDto clientDto){

        return ResponseEntity.ok(clientServiceImple.updateClient(id, clientDto));
    }

}
