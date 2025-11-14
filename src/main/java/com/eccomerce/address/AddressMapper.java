package com.eccomerce.address;

import org.mapstruct.MapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private final ModelMapper modelMapper;

    public AddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Address convertAddressDtoToAddress(AddressDto addressDto){

        return modelMapper.map(addressDto, Address.class);
    }

    public AddressDto convertAddressToAddressDto(Address address){

        return modelMapper.map(address, AddressDto.class);
    }

}
