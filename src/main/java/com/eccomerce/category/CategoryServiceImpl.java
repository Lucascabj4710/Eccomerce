package com.eccomerce.category;

import com.eccomerce.category.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void createCategory(CategoryDto categoryDto) {

        Category category = Category.builder()
                .name(categoryDto.getName())
                .build();

        categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Long id) {

        categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("La categoria ingresada no existe"));
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("La categoria ingresada no existe"));

        category.setName(categoryDto.getName());

        categoryRepository.save(category);
    }
}
