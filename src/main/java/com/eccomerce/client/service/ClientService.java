package com.eccomerce.client.service;

import com.eccomerce.client.dto.ClientDto;
import com.eccomerce.client.dto.ClientResponseDto;
import com.eccomerce.client.entity.Client;

import java.util.List;
import java.util.Map;

public interface ClientService {

    // Crear cliente
    public Map<String, String> createClient(ClientDto clientDto);

    // Crear cliente Admin
    public Map<String, String> createClientAdmin(ClientDto clientDto);


    // Obtener clientes
    public List<ClientResponseDto> getClients();

    // Obtener cliente por ID
    public ClientResponseDto getClientId(Long id);

    // Eliminar clientee por ID
    public Map<String, String> deleteClient(Long id);

    // Actualizar cliente por ID
    public Map<String, String> updateClient(ClientDto clientDto, String username);

    public Client getUserActive(String username);

}
