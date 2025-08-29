package com.eccomerce.client.controller;

import com.eccomerce.client.dto.ClientDto;
import com.eccomerce.client.service.ClientServiceImpl;
import jakarta.validation.Valid;
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

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientDto clientDto){

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

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateClient(@RequestBody @Valid ClientDto clientDto, @PathVariable String username){

        return ResponseEntity.ok(clientServiceImple.updateClient(clientDto, username));
    }

    @GetMapping("/getUserActive/{username}")
    public ResponseEntity<?> getUserActive(@PathVariable String username){

        return ResponseEntity.ok(clientServiceImple.getUserActive(username));
    }

}
