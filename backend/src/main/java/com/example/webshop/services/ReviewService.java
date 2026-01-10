package com.example.webshop.services;

import com.example.webshop.DTO.AddReviewDTO;
import com.example.webshop.entities.Product;
import com.example.webshop.entities.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    @PersistenceContext
    EntityManager entityManager;

    public ReviewService() {
    }

    public List<Review> getAllReviews() {
        return entityManager.createQuery("select r from Review r where r.isDeleted=false")
                .getResultList();
    }

    public List<Review> getFilteredReviewsByRating(int rateValue) {
        return entityManager.createQuery("select r from Review r where r.rating=:rating and r.isDeleted=false")
                .setParameter("rating", rateValue)
                .getResultList();
    }

    public List<Review> getSortedReviews() {
        return entityManager.createQuery("select r from Review r where r.isDeleted=false order by r.rating")
                .getResultList();
    }

    public Double getAvgRate(Product p) {
        Double avg = (Double) entityManager.createQuery("select avg(r.rating) from Review r where r.product=:ratedProduct and r.isDeleted=false")
                .setParameter("ratedProduct",p)
                .getSingleResult();

        return avg != null ? avg : 0;
    }

    @Transactional
    public void deleteReview(UUID id) {
        List<Review> reviews = entityManager.createQuery("select r from Review r where r.id=:id and r.isDeleted=false")
                .setParameter("id",id)
                .getResultList();

        if (!reviews.isEmpty()) {
            Review review = reviews.getFirst();
            review.setDeleted(true);
        }
    }

    @Transactional
    public void addReview(AddReviewDTO addReviewDTO) {
        List<Product> products = entityManager.createQuery("select p from Product p where p.productId=:productId")
                .setParameter("productId", addReviewDTO.productId())
                .getResultList();

        if (!products.isEmpty()) {
            Review review = new Review(products.getFirst(), addReviewDTO.rating(), addReviewDTO.text());
            entityManager.persist(review);
        }
    }
}
