package com.eccomerce.product.service;

import com.eccomerce.category.exception.CategoryNotFoundException;
import com.eccomerce.product.dto.ProductDto;
import com.eccomerce.product.dto.ProductResponseDto;
import com.eccomerce.product.entity.Product;
import com.eccomerce.product.exception.ProductNotFoundException;
import com.eccomerce.category.Category;
import com.eccomerce.product.mapper.ProductDtoMapper;
import com.eccomerce.product.mapper.ProductMapper;
import com.eccomerce.product.repository.ProductRepository;
import com.eccomerce.category.CategoryRepository;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import com.eccomerce.product.entity.IsEnabledEnum;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductDtoMapper productDtoMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // Inyectamos la ruta desde application.properties
    @Value("${img.folder.path}")
    private String imgFolder;

    // Validar tamaño (ej: max 5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB


    public ProductServiceImpl(ProductMapper productMapper, ProductDtoMapper productDtoMapper,
                              ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productDtoMapper = productDtoMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Map<String, String> createProduct(ProductDto productDto, MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            logger.info("[CREATE_PRODUCT] Inicio del método createProduct");

            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                logger.warn("[CREATE_PRODUCT] Nombre de archivo no válido");
                return Map.of("ERROR", "Nombre de archivo inválido");
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException(
                        "El archivo es demasiado grande. Máximo permitido: 5MB");
            }

            // Obtenemos la extensión (ya limpia)
            String extension = FilenameUtils.getExtension(originalFilename.trim()).toLowerCase();

            // Validar extensiones permitidas
            List<String> extensionesPermitidas = List.of("jpg", "jpeg", "png", "gif", "webp");
            if (!extensionesPermitidas.contains(extension)) {
                logger.warn("[CREATE_PRODUCT] Extensión no permitida: {}", extension);
                return Map.of("ERROR", "Tipo de archivo no permitido. Solo se aceptan: " + extensionesPermitidas);
            }

            // Nombre único
            String nombreArchivo = UUID.randomUUID().toString() + "." + extension;

            // Ruta absoluta calculada desde la propiedad
            Path uploadDir = Paths.get(imgFolder).toAbsolutePath();
            Path rutaArchivo = uploadDir.resolve(nombreArchivo);

            file.transferTo(rutaArchivo.toFile());

            String imageUrl = "/imgfolder/" + nombreArchivo;

            Product product = productMapper.converterToProduct(productDto);
            product.setImageUrl(imageUrl);

            IsEnabledEnum isEnabledEnum = IsEnabledEnum.ENABLED;
            product.setIsEnabled(isEnabledEnum);


            Category category = categoryRepository.findById(productDto.getIdCategory())
                    .orElseThrow(() -> {
                        logger.error("[CREATE_PRODUCT] No se encontró categoría con ID {}", productDto.getIdCategory());
                        return new CategoryNotFoundException("La categoría " + productDto.getIdCategory() + " no existe");
                    });

            product.setCategory(category);
            productRepository.save(product);

            response.put("Mensaje", "Producto cargado exitosamente");
            return response;

        } catch (DataAccessException e) {
            logger.error("[CREATE_PRODUCT] Error de acceso a datos: {}", e.getMessage(), e);
            response.put("status", "Error");
            response.put("mensaje", "Error al intentar crear producto");
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
    public Page<ProductResponseDto> getProducts(String name, String material, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        boolean hasName = name != null && !name.isBlank();
        boolean hasMaterial = material != null && !material.isBlank();

        if (hasName && !hasMaterial) {
             Page<Product> products = productRepository.findByNameContainingIgnoreCase(name, pageable);

            return products.map(productDtoMapper::converterToProductResponseDto);

        } else if (!hasName && hasMaterial) {
            Page<Product> products= productRepository.findByMaterialContainingIgnoreCase(material, pageable);

            return products.map(productDtoMapper::converterToProductResponseDto);

        } else if (hasName && hasMaterial) {
            Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndMaterialContainingIgnoreCase(name, material, pageable);

           return products.map(productDtoMapper::converterToProductResponseDto);

        } else {
            Page<Product> products = productRepository.findAllEnabled(pageable);
            return products.map(productDtoMapper::converterToProductResponseDto);
        }
    }

    @Override
    public Page<ProductResponseDto> getProductsAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productDtoMapper::converterToProductResponseDto);
    }



    @Override
    public Page<Product> getProductsByMaterial(String material, Pageable pageable) {
        return null;
    }


    @Override
    @Transactional(readOnly = true) // Implementa que esta funcion sea solo de lectura
    public ProductResponseDto getProductId(Long id) {

        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("El producto con ID " + id + " no existe"));
        ProductResponseDto productResponseDto = productDtoMapper.converterToProductResponseDto(product);
        productResponseDto.setCategoryDesc(product.getCategory().getName());

        return productResponseDto;
    }

    @Override
    public List<String> getMaterial() {
        return productRepository.findDistinctMaterials();
    }

    @Override
    public Map<String, String> deleteProduct(Long id) {
        Map<String, String> response = new HashMap<>();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("El producto con ID " + id + " no existe"));
        productRepository.delete(product);

        response.put("status","Producto eliminado con exito");

        return response;
    }

    @Override
    public Map<String, String> updateProduct(String name, ProductDto productDto, MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            Product product = productRepository.findByName(name)
                    .orElseThrow(() -> new ProductNotFoundException("El producto con nombre " + name + " no existe"));

            Category category = categoryRepository.findById(productDto.getIdCategory())
                    .orElseThrow(() -> new CategoryNotFoundException("La categoría con ID " + productDto.getIdCategory() + " no existe"));

            // Actualizar campos básicos
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setColor(productDto.getColor());
            product.setMaterial(productDto.getMaterial());
            product.setWaist(productDto.getWaist());
            product.setCategory(category);
            product.setDescription(productDto.getDescription());
            product.setStock(productDto.getStock());

            // Si viene un archivo, validamos igual que en createProduct
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();

                if (originalFilename == null || originalFilename.trim().isEmpty()) {
                    logger.warn("[UPDATE_PRODUCT] Nombre de archivo no válido");
                    return Map.of("ERROR", "Nombre de archivo inválido");
                }
                if (file.getSize() > MAX_FILE_SIZE) {
                    throw new IllegalArgumentException(
                            "El archivo es demasiado grande. Máximo permitido: 5MB");
                }

                String extension = FilenameUtils.getExtension(originalFilename.trim()).toLowerCase();
                List<String> extensionesPermitidas = List.of("jpg", "jpeg", "png", "gif", "webp");

                if (!extensionesPermitidas.contains(extension)) {
                    logger.warn("[UPDATE_PRODUCT] Extensión no permitida: {}", extension);
                    return Map.of("ERROR", "Tipo de archivo no permitido. Solo se aceptan: " + extensionesPermitidas);
                }

                // 🔹 Eliminar imagen anterior si existe
                if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                    try {
                        // Tu imagen se guarda como /imgfolder/xxx.ext → extraemos solo el nombre
                        String oldImageName = Paths.get(product.getImageUrl()).getFileName().toString();
                        Path oldImagePath = Paths.get(imgFolder).toAbsolutePath().resolve(oldImageName);
                        Files.deleteIfExists(oldImagePath);
                        logger.info("[UPDATE_PRODUCT] Imagen anterior eliminada: {}", oldImagePath);
                    } catch (IOException e) {
                        logger.warn("[UPDATE_PRODUCT] No se pudo eliminar la imagen anterior: {}", e.getMessage());
                    }
                }

                // 🔹 Guardar nueva imagen
                String nombreArchivo = UUID.randomUUID().toString() + "." + extension;
                Path uploadDir = Paths.get(imgFolder).toAbsolutePath();
                Path rutaArchivo = uploadDir.resolve(nombreArchivo);

                file.transferTo(rutaArchivo.toFile());

                String imageUrl = "/imgfolder/" + nombreArchivo;
                product.setImageUrl(imageUrl);
                logger.debug("[UPDATE_PRODUCT] URL de imagen asignada: {}", imageUrl);
            }

            productRepository.save(product);
            response.put("Status", "Producto actualizado exitosamente");
            return response;

        } catch (DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException("Error al intentar actualizar el producto", e);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }


    @Override
    @Transactional
    public Map<String, String> updateStatus(Long id) {

        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("El ID ingresado no corresponde con ningun producto"));
        if (product.getIsEnabled().name().equals("ENABLED")){
            product.setIsEnabled(IsEnabledEnum.DISABLED);
        } else {
            product.setIsEnabled(IsEnabledEnum.ENABLED);
        }



        return Map.of("Correcto", "Cambio");
    }


}
