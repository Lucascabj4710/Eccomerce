package com.eccomerce.service;

import com.eccomerce.persistence.dto.request.ClientDto;
import com.eccomerce.persistence.dto.request.ProductDto;
import com.eccomerce.persistence.dto.response.ClientResponseDto;
import com.eccomerce.persistence.dto.response.ProductResponseDto;
import com.eccomerce.persistence.entity.Product;

import java.util.List;
import java.util.Map;

public interface ClientService {

    // Crear cliente
    public Map<String, String> createClient(ClientDto clientDto);

    // Obtener clientes
    public List<ClientResponseDto> getClients();

    // Obtener cliente por ID
    public ClientResponseDto getClientId(Long id);

    // Eliminar clientee por ID
    public Map<String, String> deleteClient(Long id);

    // Actualizar cliente por ID
    public Map<String, String> updateClient(ClientDto clientDto);

}
