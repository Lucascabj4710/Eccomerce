package com.eccomerce.address;

import com.eccomerce.client.entity.Client;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.client.repository.ClientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public AddressDto getAddress(Long clientId) {

        String username = getUsername();
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("Error el cliente solicitado no existe"));



        return null;
    }


    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
