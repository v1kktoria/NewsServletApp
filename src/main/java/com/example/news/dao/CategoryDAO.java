package com.example.news.dao;

import com.example.news.model.Category;

import java.util.List;

public interface CategoryDAO {
    void saveCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
}
