package com.eccomerce.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllCategory(){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryAll());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getCategoryID(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){

        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body("La operacion se realizo con exito");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){

        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body("La operacion se realizo con exito");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto){

        categoryService.updateCategory(id,categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body("La operacion se realizo con exito");
    }

}
