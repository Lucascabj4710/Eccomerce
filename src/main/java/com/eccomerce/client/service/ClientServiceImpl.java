package com.eccomerce.client.service;

import com.eccomerce.client.dto.ClientDto;
import com.eccomerce.client.dto.ClientResponseDto;
import com.eccomerce.client.entity.Client;
import com.eccomerce.client.exception.ClientCreationException;
import com.eccomerce.client.exception.ClientDeleteException;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.client.exception.ClientUpdateException;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.userEntity.UserEntityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Slf4j
@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final UserEntityServiceImpl userEntityService;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, UserEntityServiceImpl userEntityService, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.userEntityService = userEntityService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Map<String, String> createClient(ClientDto clientDto) {

            Client client = convertToClient(clientDto);
            client.setUserEntity(userEntityService.createUserEntity(clientDto.getUserEntityDto().getUsername(), clientDto.getUserEntityDto().getPassword()));

            clientRepository.save(client);

            return Map.of("COMPLETED", "CLIENT CREATED");

    }

    @Override
    public Map<String, String> createClientAdmin(ClientDto clientDto) {
        Client client = convertToClient(clientDto);
        client.setUserEntity(userEntityService.createUserEntity(clientDto.getUserEntityDto().getUsername(), clientDto.getUserEntityDto().getPassword()));

        clientRepository.save(client);

        return Map.of("COMPLETED", "CLIENT CREATED");
    }

    @Override
    public List<ClientResponseDto> getClients() {

        return clientRepository.findAll().stream()
                .map(this::convertToClientResponseDto)
                .toList();
    }

    @Override
    public ClientResponseDto getClientId(Long id) {

        return clientRepository.findById(id).map(this::convertToClientResponseDto).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Map<String, String> deleteClient(Long id) {

        clientRepository.findById(id).orElseThrow(()-> new ClientNotFoundException("El cliente solicitado no existe"));
            clientRepository.deleteById(id);
        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public Map<String, String> updateClient(ClientDto clientDto, String username) {

        log.info(username);

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("Error no existe un cliente con ese ID"));
        client.setName(clientDto.getName());
        client.setDni(clientDto.getDni());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setLastName(clientDto.getLastname());

        clientRepository.save(client);

        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public Client getUserActive(String username) {

        return clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("No existe el cliente con ese username"));
    }


    public Client convertToClient(ClientDto clientDto){
        if (modelMapper.getTypeMap(ClientDto.class, Client.class) == null){
            TypeMap<ClientDto, Client> propertyMapper = modelMapper.createTypeMap(ClientDto.class, Client.class);
            propertyMapper.setPropertyCondition(Conditions.isNotNull());
            propertyMapper.addMappings(mapper -> mapper.skip(Client::setId));
            propertyMapper.addMapping(ClientDto::getName, Client::setName);
            propertyMapper.addMapping(ClientDto::getLastname, Client::setLastName);
            propertyMapper.addMapping(ClientDto::getDni, Client::setDni);
            propertyMapper.addMapping(ClientDto::getEmail, Client::setEmail);
            propertyMapper.addMapping(ClientDto::getPhoneNumber, Client::setPhoneNumber);
            propertyMapper.addMapping(ClientDto::getUserEntityDto, Client::setUserEntity);
        }
        return modelMapper.map(clientDto, Client.class);
    }

    public ClientResponseDto convertToClientResponseDto(Client client){
        if (modelMapper.getTypeMap(Client.class, ClientResponseDto.class) == null){
            TypeMap<Client, ClientResponseDto> propertyMapper = modelMapper.createTypeMap(Client.class, ClientResponseDto.class);
            propertyMapper.addMapping(Client::getName, ClientResponseDto::setName);
            propertyMapper.addMapping(Client::getLastName, ClientResponseDto::setLastname);
            propertyMapper.addMapping(Client::getDni, ClientResponseDto::setDni);
            propertyMapper.addMapping(Client::getEmail, ClientResponseDto::setEmail);
            propertyMapper.addMapping(Client::getPhoneNumber, ClientResponseDto::setPhoneNumber);


        }
        return modelMapper.map(client, ClientResponseDto.class);
    }

}
