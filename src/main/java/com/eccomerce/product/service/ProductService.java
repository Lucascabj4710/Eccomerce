package com.eccomerce.product.service;

import com.eccomerce.product.entity.Product;
import com.eccomerce.product.dto.ProductDto;
import com.eccomerce.product.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {

    // Crear producto
    public Map<String, String> createProduct(ProductDto productDto, MultipartFile archivo);

    // Obtener productos
    public List<Product> getProducts();

    // Obtener producto por ID
    public ProductResponseDto getProductId(Long id);

    // Eliminar producto por ID
    public Map<String, String> deleteProduct(Long id);

    // Actualizar producto por ID
    public Map<String, String> updateProduct(Long id, ProductDto product, MultipartFile archivo);
}
