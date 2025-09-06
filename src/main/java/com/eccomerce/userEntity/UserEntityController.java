package com.eccomerce.userEntity;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserEntityController {

    private final UserEntityServiceImpl userEntityService;
    private final UserDetailService userDetailService;

    public UserEntityController(UserEntityServiceImpl userEntityService, UserDetailService userDetailService) {
        this.userEntityService = userEntityService;
        this.userDetailService = userDetailService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserEntityDto userEntityDto){

        return new ResponseEntity<>(userDetailService.login(userEntityDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserPass(@RequestBody @Valid UserEntityDto userEntityDto){

        return userEntityService.updateUserEntity( userEntityDto);
    }

}
