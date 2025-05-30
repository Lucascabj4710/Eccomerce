package com.eccomerce.service;

import com.eccomerce.persistence.dto.request.ProductDto;
import com.eccomerce.persistence.dto.response.ProductResponseDto;
import com.eccomerce.persistence.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    // Crear producto
    public Map<String, String> createProduct(ProductDto productDto);

    // Obtener productos
    public List<Product> getProducts();

    // Obtener producto por ID
    public ProductResponseDto getProductId(Long id);

    // Eliminar producto por ID
    public Map<String, String> deleteProduct(Long id);

    // Actualizar producto por ID
    public Map<String, String> updateProduct(Long id, ProductDto product);
}
