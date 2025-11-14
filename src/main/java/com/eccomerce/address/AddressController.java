package com.eccomerce.address;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressServiceImpl addressService;

    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createAddress(@RequestBody @Valid AddressDto addressDto){

        addressService.createAddress(addressDto);

        return new ResponseEntity<>("COMPLETED", HttpStatus.CREATED);
    }

    @PostMapping("get")
    public ResponseEntity<?> getAddress(@PathVariable Long clienteId){

        return new ResponseEntity<>("COMPLETED", HttpStatus.CREATED);
    }

}
