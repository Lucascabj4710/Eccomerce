package com.eccomerce.service;

import com.eccomerce.persistence.dto.ProductDto;
import com.eccomerce.persistence.dto.ProductResponseDto;
import com.eccomerce.persistence.entity.Category;
import com.eccomerce.persistence.entity.Product;
import com.eccomerce.persistence.repository.CategoryRepository;
import com.eccomerce.persistence.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Service
public class ProductServiceImpl implements ProductService{

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Map<String,String> createProduct(ProductDto productDto) {
        Map<String, String> response = new HashMap<>();
        try {
            logger.info("Inicio del metodo createProduct");

            Product product = converterToProduct(productDto);
            Category category = categoryRepository.findById(productDto.getIdCategory())
                    .orElseThrow(()-> new NoSuchElementException("La categoria " + productDto.getIdCategory() + " no existe"));

            product.setCategory(category);
            productRepository.save(product);
            response.put("Mensaje", "Producto cargado exitosamente");

            return response;
        } catch (DataAccessException e) {
            response.put("status", "Error");
            response.put("mensaje", "error al intentar crear producto");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }

    }

    @Override
    public List<Product> getProducts() {
        try {
            logger.info("Inicio del metodo getProducts");
            return productRepository.findAll();
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error al intentar recuperar la lista de productos");
        }

    }

    @Override
    public ProductResponseDto getProductId(Long id) {

        Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("El producto con ID " + id + " no existe"));
        ProductResponseDto productResponseDto = converterToProductResponseDto(product);
        productResponseDto.setCategoryDesc(product.getCategory().getName());

        return productResponseDto;
    }

    @Override
    public Map<String, String> deleteProduct(Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            productRepository.deleteById(id);
            response.put("status","Producto eliminado con exito");

            return response;
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error al intentar eliminar el producto");
        }
    }

    @Override
    public Map<String, String> updateProduct(Long id, ProductDto productDto) {
        Map<String, String> response = new HashMap<>();

        try {
            Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("El producto con ID " + id + " no existe"));
            Category category = categoryRepository.findById(productDto.getIdCategory()).orElseThrow(()-> new NoSuchElementException("La categoria con ID " + productDto.getIdCategory() + " no existe"));

            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setColor(productDto.getColor());
            product.setMaterial(productDto.getMaterial());
            product.setWaist(productDto.getWaist());
            product.setCategory(category);
            response.put("Status","Producto actualizado exitosamente");

            productRepository.save(product);

            return response;
        } catch (RuntimeException e) {
            throw new DataAccessResourceFailureException("Error al intentar actualizar el producto");
        }
    }

    public ProductResponseDto converterToProductResponseDto(Product product){

        if (modelMapper.getTypeMap(Product.class, ProductResponseDto.class) == null) {
            TypeMap<Product, ProductResponseDto> propertyMapper = modelMapper.createTypeMap(Product.class, ProductResponseDto.class);
            propertyMapper.addMapping(Product::getName, ProductResponseDto::setName);
            propertyMapper.addMapping(Product::getPrice, ProductResponseDto::setPrice);
            propertyMapper.addMapping(Product::getColor,ProductResponseDto::setColor);
            propertyMapper.addMapping(Product::getMaterial, ProductResponseDto::setMaterial);
            propertyMapper.addMapping(Product::getWaist,ProductResponseDto::setWaist);
        }

        return modelMapper.map(product, ProductResponseDto.class);
    }

    public Product converterToProduct(ProductDto productDto){

        if (modelMapper.getTypeMap(ProductDto.class, Product.class) == null) {
            TypeMap<ProductDto, Product> propertyMapper = modelMapper.createTypeMap(ProductDto.class, Product.class);
            propertyMapper.addMappings(mapper -> mapper.skip(Product::setId)); // Ignorar id
            propertyMapper.addMapping(ProductDto::getName, Product::setName);
            propertyMapper.addMapping(ProductDto::getPrice, Product::setPrice);
            propertyMapper.addMapping(ProductDto::getColor, Product::setColor);
            propertyMapper.addMapping(ProductDto::getMaterial, Product::setMaterial);
            propertyMapper.addMapping(ProductDto::getWaist, Product::setWaist);
        }

        return modelMapper.map(productDto, Product.class);
    }


}
