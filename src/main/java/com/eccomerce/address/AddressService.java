package com.eccomerce.address;

public interface AddressService {

    public void createAddress(AddressDto addressDto);
    public AddressDto getAddress(Long clientId);

}
