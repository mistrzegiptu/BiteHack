package com.example.webshop.controllers;

import com.example.webshop.DTO.AddReviewDTO;
import com.example.webshop.DTO.DeleteReviewDTO;
import com.example.webshop.DTO.ReviewResponseDTO;
import com.example.webshop.entities.Review;
import com.example.webshop.services.ReviewService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/review"))
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/adding-review")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody AddReviewDTO dto) {
        var review = reviewService.addReview(dto);
        return review != null ? ResponseEntity.ok(mapToDTO(review)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<ReviewResponseDTO>> getSortedReviews() {
        var reviews = reviewService.getSortedReviews();
        return ResponseEntity.ok(reviews.stream().map(ReviewController::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/rate-filtred/{rateValue}")
    public ResponseEntity<List<ReviewResponseDTO>> getFiltredReviews(@PathVariable int rateValue) {
        List<Review> reviews = reviewService.getFilteredReviewsByRating(rateValue);
        return ResponseEntity.ok(reviews.stream().map(ReviewController::mapToDTO).collect(Collectors.toList()));
    }

    @PatchMapping("/delete-review")
    public ResponseEntity<Void> deleteReview(@RequestBody DeleteReviewDTO dto) {
        if (reviewService.deleteReview(dto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/average-rate")
    public ResponseEntity<Double> getAvg(UUID productId) {
        Double avg = reviewService.getAvgRate(productId);
        return avg != null ? ResponseEntity.ok(avg) : ResponseEntity.notFound().build();
    }

    private static ReviewResponseDTO mapToDTO(Review review) {
        return new ReviewResponseDTO(review.getReviewId(), review.getRating(), review.getText(), review.getUser().getLogin());
    }
}
