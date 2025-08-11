package com.eccomerce.category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    CategoryDto getCategory(Long id);
    List<CategoryDto> getCategoryAll();
    Map<String, String> createCategory(CategoryDto categoryDto);
    Map<String, String> deleteCategory(Long id);
    Map<String, String> updateCategory(Long id, CategoryDto categoryDto);
}
