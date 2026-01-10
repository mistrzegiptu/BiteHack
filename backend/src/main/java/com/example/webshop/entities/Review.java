package com.example.webshop.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "Review")
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "rating")
    private int rating;

    @Column(name = "text")
    private String text;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Review() {
    }

    public Review(Product product, int rating, String text) {
        this.product = product;
        this.rating = rating;
        this.text = text;
        this.isDeleted=false;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
