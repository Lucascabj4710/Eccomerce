package com.eccomerce.service;

import com.eccomerce.persistence.dto.ClientDto;
import com.eccomerce.persistence.dto.ProductDto;
import com.eccomerce.persistence.dto.ProductResponseDto;
import com.eccomerce.persistence.entity.Product;

import java.util.List;
import java.util.Map;

public interface ClientService {

    // Crear cliente
    public Map<String, String> createClient(ClientDto clientDto);

    // Obtener clientes
    public List<Product> getClients();

    // Obtener cliente por ID
    public ProductResponseDto getClientId(Long id);

    // Eliminar clientee por ID
    public Map<String, String> deleteClient(Long id);

    // Actualizar cliente por ID
    public Map<String, String> updateClient(Long id, ProductDto product);

}
