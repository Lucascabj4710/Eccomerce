package com.eccomerce.controller;

import com.eccomerce.persistence.dto.request.ProductDto;
import com.eccomerce.service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService){
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){

        productService.createProduct(productDto);

        return new ResponseEntity<>("Completed", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductID(@PathVariable Long id){

        return new ResponseEntity<>(productService.getProductId(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProduct(){

        return new ResponseEntity<>(productService.getProducts(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id){
        productService.updateProduct(id,productDto);
        return new ResponseEntity<>("COMPLETED UPDATE", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){

        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

}
