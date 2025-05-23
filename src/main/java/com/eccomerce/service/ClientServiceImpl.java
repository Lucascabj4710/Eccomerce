package com.eccomerce.service;

import com.eccomerce.persistence.dto.ClientDto;
import com.eccomerce.persistence.dto.ProductDto;
import com.eccomerce.persistence.dto.ProductResponseDto;
import com.eccomerce.persistence.entity.Client;
import com.eccomerce.persistence.entity.Product;
import com.eccomerce.persistence.repository.ClientRepository;
import com.eccomerce.persistence.repository.UserEntityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<String, String> createClient(ClientDto clientDto) {



        return Map.of();
    }

    @Override
    public List<Product> getClients() {
        return List.of();
    }

    @Override
    public ProductResponseDto getClientId(Long id) {
        return null;
    }

    @Override
    public Map<String, String> deleteClient(Long id) {
        return Map.of();
    }

    @Override
    public Map<String, String> updateClient(Long id, ProductDto product) {
        return Map.of();
    }

    public Client convertToClient(ClientDto clientDto){
        if (modelMapper.getTypeMap(ClientDto.class, Client.class) == null){
            TypeMap<ClientDto, Client> propertyMapper = modelMapper.createTypeMap(ClientDto.class, Client.class);
            propertyMapper.addMappings(mapper -> mapper.skip(Client::setId));
            propertyMapper.addMappings(mapper -> mapper.skip(Client::setAddressList));
            propertyMapper.addMapping(ClientDto::getName, Client::setName);
        }
        return modelMapper.map(clientDto, Client.class);
    }


}
