package com.eccomerce.address;

import java.util.List;

public interface AddressService {

    public void createAddress(AddressDto addressDto);
    public List<AddressDto> getAddress();
    public void updateAddress(Long idAddress, AddressDto addressDto);

}
