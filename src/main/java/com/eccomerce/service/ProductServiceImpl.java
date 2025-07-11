package com.eccomerce.service;

import com.eccomerce.exception.ProductNotFoundException;
import com.eccomerce.persistence.dto.request.ProductDto;
import com.eccomerce.persistence.dto.response.ProductResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
public class ProductServiceImpl implements ProductService{

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    // Definilo en tu clase Service (fuera del método)
    private final String imgFolder = "C:/Users/lucas/Documents/IntelliJ proyectos/eccomerce/src/main/resources/static/imgfolder";


    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Map<String, String> createProduct(ProductDto productDto, MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            logger.info("[CREATE_PRODUCT] Inicio del método createProduct");

            logger.debug("[CREATE_PRODUCT] DTO recibido: {}", productDto);
            logger.debug("[CREATE_PRODUCT] ID de categoría recibido: {}", productDto.getIdCategory());

            // Verifica si el archivo está vacío.
            if (file.isEmpty()) {
                logger.warn("[CREATE_PRODUCT] Archivo recibido está vacío");
                return Map.of("ERROR", "Archivo vacío");
            }

            logger.info("[CREATE_PRODUCT] Archivo recibido: Nombre original = {}, Tamaño = {} bytes",
                    file.getOriginalFilename(), file.getSize());

            String nombreArchivo = UUID.randomUUID() + file.getOriginalFilename();
            Path rutaArchivo = Paths.get(imgFolder).resolve(nombreArchivo);

            logger.debug("[CREATE_PRODUCT] Ruta de archivo generada: {}", rutaArchivo);

            Files.copy(file.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            logger.info("[CREATE_PRODUCT] Archivo copiado correctamente en {}", rutaArchivo);

            String imageUrl = "/imgfolder/" + nombreArchivo.replaceFirst("^/+", "");
            logger.debug("[CREATE_PRODUCT] URL de imagen asignada: {}", imageUrl);

            Product product = converterToProduct(productDto);
            product.setImageUrl(imageUrl);

            logger.debug("[CREATE_PRODUCT] Producto convertido desde DTO: {}", product);

            Category category = categoryRepository.findById(productDto.getIdCategory())
                    .orElseThrow(() -> {
                        logger.error("[CREATE_PRODUCT] No se encontró categoría con ID {}", productDto.getIdCategory());
                        return new NoSuchElementException("La categoría " + productDto.getIdCategory() + " no existe");
                    });

            logger.info("[CREATE_PRODUCT] Categoría encontrada: {}", category.getName());

            product.setCategory(category);
            productRepository.save(product);

            logger.info("[CREATE_PRODUCT] Producto guardado exitosamente con ID {}", product.getId());

            response.put("Mensaje", "Producto cargado exitosamente");
            return response;

        } catch (DataAccessException e) {
            logger.error("[CREATE_PRODUCT] Error de acceso a datos: {}", e.getMessage(), e);
            response.put("status", "Error");
            response.put("mensaje", "Error al intentar crear producto");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        } catch (IOException e) {
            logger.error("[CREATE_PRODUCT] Error de IO al procesar archivo: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("[CREATE_PRODUCT] Error inesperado: {}", e.getMessage(), e);
            throw e;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        try {
            logger.info("Inicio del metodo getProducts");
            return productRepository.findAll();
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error al intentar recuperar la lista de productos");
        }

    }

    @Override
    @Transactional(readOnly = true) // Implementa que esta funcion sea solo de lectura
    public ProductResponseDto getProductId(Long id) {

        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("El producto con ID " + id + " no existe"));
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
    public Map<String, String> updateProduct(Long id, ProductDto productDto, MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("El producto con ID " + id + " no existe"));
            Category category = categoryRepository.findById(productDto.getIdCategory()).orElseThrow(()-> new NoSuchElementException("La categoria con ID "
                    + productDto.getIdCategory() + " no existe"));

            product.setName(productDto.getName());
            product.setImageUrl(product.getImageUrl());
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
            propertyMapper.addMapping(Product::getImageUrl, ProductResponseDto::setImageUrl);
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
            propertyMapper.addMappings(mapper -> mapper.skip(Product::setImageUrl)); // Ignorar url
            propertyMapper.addMapping(ProductDto::getName, Product::setName);
            propertyMapper.addMapping(ProductDto::getPrice, Product::setPrice);
            propertyMapper.addMapping(ProductDto::getColor, Product::setColor);
            propertyMapper.addMapping(ProductDto::getMaterial, Product::setMaterial);
            propertyMapper.addMapping(ProductDto::getWaist, Product::setWaist);
        }

        return modelMapper.map(productDto, Product.class);
    }


}
