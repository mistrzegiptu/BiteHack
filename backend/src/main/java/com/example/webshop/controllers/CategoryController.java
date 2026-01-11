package com.example.webshop.controllers;

import com.example.webshop.entities.Category;
import com.example.webshop.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody String categoryName) {
        categoryName = categoryName.replace("\"", "").replace("'", "").trim();
        Category category = categoryService.createCategory(categoryName);

        return ResponseEntity.ok(category);
    }
}
