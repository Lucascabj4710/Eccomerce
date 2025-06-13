package com.eccomerce.service;

import com.eccomerce.persistence.dto.request.ClientDto;
import com.eccomerce.persistence.dto.response.ClientResponseDto;
import com.eccomerce.persistence.entity.Client;
import com.eccomerce.persistence.entity.RoleEntity;
import com.eccomerce.persistence.entity.UserEntity;
import com.eccomerce.persistence.repository.ClientRepository;
import com.eccomerce.persistence.repository.RoleEntityRepository;
import com.eccomerce.persistence.repository.UserEntityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<String, String> createClient(ClientDto clientDto) {
        try {
            Client client = convertToClient(clientDto);
            String password = passwordEncoder.encode(client.getUserEntity().getPassword());
            RoleEntity roleUser = roleEntityRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("El rol ingresado no existe"));
            client.setUserEntity(UserEntity.builder()
                            .isEnabled(true)
                            .credentialsNonExpired(true)
                            .accountNonLocked(true)
                            .accountNonExpired(true)
                            .roleEntities(Set.of(roleUser))
                            .password(password)
                            .username(clientDto.getName())
                    .build());

            clientRepository.save(client);

            return Map.of("COMPLETED", "CLIENT CREATED");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

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

        clientRepository.findById(id).orElseThrow(NoSuchElementException::new);

        try {
            clientRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public Map<String, String> updateClient(Long id, ClientDto clientDto) {

        clientRepository.findById(id).orElseThrow(NoSuchElementException::new);

        try {
            Client client = convertToClient(clientDto);
            clientRepository.save(client);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return Map.of("STATUS", "COMPLETED");
    }


    public Client convertToClient(ClientDto clientDto){
        if (modelMapper.getTypeMap(ClientDto.class, Client.class) == null){
            TypeMap<ClientDto, Client> propertyMapper = modelMapper.createTypeMap(ClientDto.class, Client.class);
            propertyMapper.addMappings(mapper -> mapper.skip(Client::setId));
            propertyMapper.addMapping(ClientDto::getName, Client::setName);
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
