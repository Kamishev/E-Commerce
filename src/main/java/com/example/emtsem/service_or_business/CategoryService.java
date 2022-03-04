package com.example.emtsem.service_or_business;

import com.example.emtsem.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    List<Category> findAllByName(String name);
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    Category updateName(Long id, String name);
    void deleteById(Long id);
}
