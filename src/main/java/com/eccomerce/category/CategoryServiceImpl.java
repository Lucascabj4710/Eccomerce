package com.eccomerce.category;

import com.eccomerce.category.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl (CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("La categoria ingresada no existe"));
        return CategoryDto.builder().name(category.getName()).build();
    }

    @Override
    public List<CategoryDto> getCategoryAll() {

        return categoryRepository.findAll().stream()
                .map(category -> CategoryDto.builder().name(category.getName()).idCategory(category.getId()).build())
                .toList();
    }

    @Override
    public Map<String, String> createCategory(CategoryDto categoryDto) {

        Category category = Category.builder()
                .name(categoryDto.getName())
                .build();

        categoryRepository.save(category);

        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public Map<String, String> deleteCategory(Long id) {

        categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("La categoria ingresada no existe"));
        categoryRepository.deleteById(id);

        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public Map<String, String> updateCategory(Long id, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("La categoria ingresada no existe"));

        category.setName(categoryDto.getName());

        categoryRepository.save(category);

        return Map.of("STATUS", "COMPLETED");
    }
}
