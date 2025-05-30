package com.eccomerce.service;

import com.eccomerce.persistence.dto.request.ClientDto;
import com.eccomerce.persistence.dto.request.ProductDto;
import com.eccomerce.persistence.dto.response.ProductResponseDto;
import com.eccomerce.persistence.entity.Client;
import com.eccomerce.persistence.entity.Product;
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
            propertyMapper.addMapping(ClientDto::getName, Client::setName);
            propertyMapper.addMapping(ClientDto::getDni, Client::setDni);
            propertyMapper.addMapping(ClientDto::getEmail, Client::setEmail);
            propertyMapper.addMapping(ClientDto::getPhoneNumber, Client::setPhoneNumber);
            propertyMapper.addMapping(ClientDto::getUserEntityDto, Client::setUserEntity);
        }
        return modelMapper.map(clientDto, Client.class);
    }


}
