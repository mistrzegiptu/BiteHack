package com.example.webshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "Category")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "name")
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
