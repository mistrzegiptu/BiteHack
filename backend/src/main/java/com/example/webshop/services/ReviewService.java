package com.example.webshop.services;

import com.example.webshop.DTO.AddReviewDTO;
import com.example.webshop.DTO.DeleteReviewDTO;
import com.example.webshop.entities.Product;
import com.example.webshop.entities.Review;
import com.example.webshop.entities.User;
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

    public Double getAvgRate(UUID productId) {
        List<Product> products = entityManager.createQuery("select p from Product p where p.productId=:productId",Product.class)
                .setParameter("productId", productId)
                .getResultList();

        if (products.isEmpty()) {
            return null;
        }

        Double avg = (Double) entityManager.createQuery("select avg(r.rating) from Review r where r.product=:ratedProduct and r.isDeleted=false")
                .setParameter("ratedProduct",products.getFirst())
                .getSingleResult();

        return avg != null ? avg : 0;
    }

    @Transactional
    public boolean deleteReview(DeleteReviewDTO dto) {
        List<Review> reviews = entityManager.createQuery("select r from Review r where r.id=:id and r.isDeleted=false", Review.class)
                .setParameter("id",dto.reviewId())
                .getResultList();

        List<User> users = entityManager.createQuery("select u from Users u where u.login=:login", User.class)
                .setParameter("login", dto.userLogin())
                .getResultList();

        if (!reviews.isEmpty() && !users.isEmpty()) {
            Review review = reviews.getFirst();
            User user = users.getFirst();

            review.setDeleted(true);
            return true;
        }

        return false;
    }

    @Transactional
    public Review addReview(AddReviewDTO addReviewDTO) {
        List<Product> products = entityManager.createQuery("select p from Product p where p.productId=:productId")
                .setParameter("productId", addReviewDTO.productId())
                .getResultList();

        List<User> users = entityManager.createQuery("select u from Users u where u.login=:login")
                .setParameter("login",addReviewDTO.login())
                .getResultList();

        if (!products.isEmpty() && !users.isEmpty()) {
            Review review = new Review(products.getFirst(), addReviewDTO.rating(), addReviewDTO.text(), users.getFirst().getLogin());
            entityManager.persist(review);
            return review;
        }
        return null;
    }
}
