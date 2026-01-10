package com.example.webshop.controllers;

import com.example.webshop.DTO.ProductDTO;
import com.example.webshop.entities.Product;
import com.example.webshop.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<List<Product>> getAllProducts() {
        var products =  productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{name}")
    public @ResponseBody ResponseEntity<Product> getProductByName(@PathVariable String name) {
        var product = productService.getProductByName(name);

        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryName}")
    public @ResponseBody ResponseEntity<List<Product>> getProductByCategory(@PathVariable String categoryName) {
        var products =  productService.getFilteredByCategory(categoryName);

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/orderByName")
    public @ResponseBody ResponseEntity<List<Product>> getProductsByName() {
        var products =  productService.getSortedByName();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/orderByAmount")
    public @ResponseBody ResponseEntity<List<Product>> getProductsByAmount() {
        var products =  productService.getSortedByAmount();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/orderByPrice")
    public @ResponseBody ResponseEntity<List<Product>> getProductsByPrice() {
        var products =  productService.getSortedByPrice();

        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDto) {
        Product newProduct = productService.addProduct(productDto.name(), productDto.categoryName(), productDto.price(), productDto.amount(), productDto.imageUrl());

        return ResponseEntity.ok(newProduct);
    }
}
