package com.example.webshop.controllers;

import com.example.webshop.DTO.ProductDTO;
import com.example.webshop.DTO.ProductResponseDTO;
import com.example.webshop.entities.Product;
import com.example.webshop.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        var products =  productService.getAllProducts();

        return ResponseEntity.ok(products.stream().map(ProductController::mapProduct).collect(Collectors.toList()));
    }

    @GetMapping("/name/{name}")
    public @ResponseBody ResponseEntity<ProductResponseDTO> getProductByName(@PathVariable String name) {
        var product = productService.getProductByName(name);

        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapProduct(product));
    }

    @GetMapping("/category/{categoryName}")
    public @ResponseBody ResponseEntity<List<ProductResponseDTO>> getProductByCategory(@PathVariable String categoryName) {
        var products =  productService.getFilteredByCategory(categoryName);

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products.stream().map(ProductController::mapProduct).collect(Collectors.toList()));
    }

    @GetMapping("/orderByName")
    public @ResponseBody ResponseEntity<List<ProductResponseDTO>> getProductsByName() {
        var products =  productService.getSortedByName();

        return ResponseEntity.ok(products.stream().map(ProductController::mapProduct).collect(Collectors.toList()));
    }

    @GetMapping("/orderByAmount")
    public @ResponseBody ResponseEntity<List<ProductResponseDTO>> getProductsByAmount() {
        var products =  productService.getSortedByAmount();

        return ResponseEntity.ok(products.stream().map(ProductController::mapProduct).collect(Collectors.toList()));
    }

    @GetMapping("/orderByPrice")
    public @ResponseBody ResponseEntity<List<ProductResponseDTO>> getProductsByPrice() {
        var products =  productService.getSortedByPrice();

        return ResponseEntity.ok(products.stream().map(ProductController::mapProduct).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductDTO productDto) {
        Product newProduct = productService.addProduct(productDto.name(), productDto.categoryName(), productDto.price(), productDto.amount(), productDto.imageUrl());

        return ResponseEntity.ok(mapProduct(newProduct));
    }

    private static ProductResponseDTO mapProduct(Product product) {
        return new ProductResponseDTO(product.getProductId(), product.getName(), product.getCategory().getName(), product.getPrice(), product.getAmount(), product.getImagePath());
    }
}
