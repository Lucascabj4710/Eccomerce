package com.eccomerce.product.service;

import com.eccomerce.product.entity.Product;
import com.eccomerce.product.dto.ProductDto;
import com.eccomerce.product.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {

    // Crear producto
    public Map<String, String> createProduct(ProductDto productDto, MultipartFile archivo);

    // Obtener productos
    public Page<ProductResponseDto> getProducts(String name, String material, int page, int size, String sortBy, String direction);

    public Page<Product> getProductsByMaterial(String material, Pageable pageable);

    // Obtener producto por ID
    public ProductResponseDto getProductId(Long id);

    // Obtener todos los materiales de todos los productos
    public List<String> getMaterial();

    // Eliminar producto por ID
    public Map<String, String> deleteProduct(Long id);

    // Actualizar producto por el nombre
    public Map<String, String> updateProduct(String name, ProductDto product, MultipartFile archivo);

}
