package com.onboard.demo.service.impl;

import com.onboard.demo.model.Category;
import com.onboard.demo.repository.CategoryRepository;
import com.onboard.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> find(String filter) {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category get(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return null;
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Override
    public boolean delete(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
