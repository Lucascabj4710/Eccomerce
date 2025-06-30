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
    private final String imgFolder = "imgfolder/"; // para la ruta en disco


    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Map<String,String> createProduct(ProductDto productDto, MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            logger.info("Inicio del metodo createProduct");
            logger.info("Hola" + productDto.getIdCategory());

            // Verifica si el archivo está vacío. Si no se subió nada, devuelve un error.
            if(file.isEmpty()){
                return Map.of("ERROR: ", "Archivo vacio");
            }

            // Genera un nombre único para el archivo usando UUID + nombre original.
            // Evita colisiones de nombres en el servidor.
            String nombreArchivo = UUID.randomUUID() + file.getOriginalFilename();

            // Define la ruta completa donde se guardará el archivo.
            // Combina la carpeta base (imgFolder) con el nombre generado.
            Path rutaArchivo = Paths.get(imgFolder).resolve(nombreArchivo);

            // Copia el archivo recibido a la carpeta del servidor.
            // Si ya existe un archivo con ese nombre, lo reemplaza.
            Files.copy(file.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            // Crea la URL accesible públicamente para la imagen.
            // Asegura que no tenga barras de más al principio.
            String imageUrl =  "/imgfolder/" + nombreArchivo.replaceFirst("^/+", "");

            // Convierte el DTO recibido (con los datos del producto) en un objeto Product real.
            Product product = converterToProduct(productDto);

            // Asigna al producto la URL de imagen generada, para que quede guardada.
            product.setImageUrl(imageUrl);

            Category category = categoryRepository.findById(productDto.getIdCategory())
                    .orElseThrow(()-> new NoSuchElementException("La categoria " + productDto.getIdCategory() + " no existe"));

            product.setCategory(category);
            productRepository.save(product);
            response.put("Mensaje", "Producto cargado exitosamente");

            return response;
        } catch (DataAccessException e) {
            logger.error("Error DataAccessException");
            response.put("status", "Error");
            response.put("mensaje", "error al intentar crear producto");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        } catch (IOException e) {
            logger.error("Error IOException");
            throw new RuntimeException(e);
        } catch (Exception e){
            logger.warn("ERROR Exception");
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
