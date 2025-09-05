package com.eccomerce.product.controller;

import com.eccomerce.product.dto.ProductDto;
import com.eccomerce.product.service.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ObjectMapper objectMapper;

    public ProductController(ProductServiceImpl productService, ObjectMapper objectMapper){
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestPart(name = "productDto") String productDto, @RequestPart(value = "file", required = false)MultipartFile archivo) throws JsonProcessingException {

        System.out.println(productDto);

        ProductDto productDto2 = objectMapper.readValue(productDto, ProductDto.class);

        productService.createProduct(productDto2, archivo);

        return new ResponseEntity<>("Completed", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductID(@PathVariable Long id){

        return new ResponseEntity<>(productService.getProductId(id), HttpStatus.ACCEPTED);
    }


    @GetMapping("/getMaterial")
    public ResponseEntity<?> getMaterial(){

        return new ResponseEntity<>(productService.getMaterial(), HttpStatus.OK);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String material, @RequestParam(required = false) String name,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "asc") String direction){

        return new ResponseEntity<>(productService.getProducts(name,material, page,size,sortBy,direction), HttpStatus.OK);
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String name,
            @ModelAttribute ProductDto productDto,
            @RequestParam(value = "file", required = false) MultipartFile archivo) {

        productService.updateProduct(name, productDto, archivo);
        return new ResponseEntity<>("COMPLETED UPDATE", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){

        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }



}
