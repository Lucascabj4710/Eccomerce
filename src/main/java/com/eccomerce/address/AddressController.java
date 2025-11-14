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

    @GetMapping("get")
    public ResponseEntity<?> getAddress(){

        return new ResponseEntity<>(addressService.getAddress(), HttpStatus.CREATED);
    }

    @PutMapping("update/{idAddress}")
    public ResponseEntity<?> updateAddress(@PathVariable @Valid Long idAddress, @RequestBody AddressDto addressDto){

        addressService.updateAddress(idAddress, addressDto);

        return new ResponseEntity<>("Update", HttpStatus.CREATED);
    }

}
