package com.eccomerce.category;

import java.util.List;

public interface CategoryService {

    CategoryDto getCategory(Long id);
    List<CategoryDto> getCategoryAll();
    void createCategory(CategoryDto categoryDto);
    void deleteCategory(Long id);
    void updateCategory(Long id, CategoryDto categoryDto);
}
