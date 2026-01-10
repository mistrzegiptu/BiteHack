package com.example.webshop.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "amount")
    private long amount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Review> reviews;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "image_path")
    private String imagePath;

    public Product() {
    }

    public Product(String name, Category category, BigDecimal price, long amount, String imagePath) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.imagePath = imagePath;
        this.isDeleted=false;
        this.reviews = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getAmount() {
        return amount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getImagePath() {
        return imagePath;
    }
}
