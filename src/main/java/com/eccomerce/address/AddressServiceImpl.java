package com.eccomerce.address;

import com.eccomerce.client.entity.Client;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.client.repository.ClientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    public final ClientRepository clientRepository;
    public final AddressRepository addressRepository;
    public final AddressMapper addressMapper;


    public AddressServiceImpl(ClientRepository clientRepository, AddressRepository addressRepository, AddressMapper addressMapper) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }


    @Override
    public void createAddress(AddressDto addressDto) {

        Client client = clientRepository.findByUsername(getUsername()).orElseThrow(()-> new ClientNotFoundException("Error el cliente solicitado no existe"));
        Address address = addressMapper.convertAddressDtoToAddress(addressDto);

        address.setClient(client);
        addressRepository.save(address);
    }

    @Override
    public List<AddressDto> getAddress() {

        String username = getUsername();


        return addressRepository.findByClientId(1L).stream()
                .map(addressMapper::convertAddressToAddressDto)
                .toList();
    }

    @Override
    public void updateAddress(Long idAddress, AddressDto addressDto) {

        Address address = addressRepository.findById(idAddress).orElseThrow(()-> new RuntimeException("NO existe la direccion indicada"));

        address.setDepartment(addressDto.getDepartment());
        address.setFloor(addressDto.getFloor());
        address.setGame(addressDto.getGame());
        address.setNumberStreet(addressDto.getNumberStreet());
        address.setZipCode(addressDto.getZipCode());
        address.setProvince(addressDto.getProvince());

        addressRepository.save(address);
    }


    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
