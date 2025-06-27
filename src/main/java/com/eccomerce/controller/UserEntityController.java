package com.eccomerce.controller;

import com.eccomerce.persistence.dto.request.UserEntityDto;
import com.eccomerce.service.UserEntityServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntityController {

    private final UserEntityServiceImpl userEntityService;

    public UserEntityController(UserEntityServiceImpl userEntityService) {
        this.userEntityService = userEntityService;
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserEntityDto userEntityDto){

        return userEntityService.createUserEntity(userEntityDto);
    }

    @GetMapping("/login")
    public String login (){
        return "login";
    }

}
