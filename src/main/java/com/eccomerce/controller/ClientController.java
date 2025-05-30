package com.eccomerce.controller;

import com.eccomerce.persistence.dto.request.ClientDto;
import com.eccomerce.service.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
