package com.example.webshop.services;

import com.example.webshop.entities.Categories;
import com.example.webshop.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    public ProductService() {
    }

    public List<Product> getAllProducts() {
        return entityManager.createQuery("from Product ").getResultList();
    }

    public Product getProductByName(String name) {
        List<Product> products = entityManager.createQuery("select p from Product p where contains(p.name, :productName) and p.isDeleted==false", Product.class)
                .setParameter("productName", name)
                .getResultList();

        if (products.isEmpty()) {
            throw new IllegalArgumentException("Product has not been found");
        }

        return products.getFirst();
    }

    public List<Product> getSortedByName() {
        return entityManager.createQuery("select p from Product p where p.isDeleted==false order by p.name", Product.class)
                .getResultList();
    }

    public List<Product> getSortedByPrice() {
        return entityManager.createQuery("select p from Product p where p.isDeleted==false order by p.price", Product.class)
                .getResultList();
    }

    public List<Product> getSortedByAmount() {
        return entityManager.createQuery("select p from Product p where p.isDeleted==false order by p.amount", Product.class)
                .getResultList();
    }

    public List<Product> getFilteredByCategory(String categoryName) {
        return entityManager.createQuery("select p from Product p where p.isDeleted==false and p.category==:category", Product.class)
                .setParameter("category", categoryName)
                .getResultList();
    }

    public List<Product> getFilteredByPrice(Optional<BigDecimal> minValueOpt, Optional<BigDecimal> maxValueOpt) {
        StringBuilder stringBuilder = new StringBuilder("select p from Product p where 1=1");

        if (minValueOpt.isPresent()) {
            stringBuilder.append(" and p.price>=:minVal");
        }
        if (maxValueOpt.isPresent()) {
            stringBuilder.append(" and p.price<=:maxVal");
        }

        var query = entityManager.createQuery(stringBuilder.toString(), Product.class);

        if (minValueOpt.isPresent()) {
            query.setParameter("minVal", minValueOpt.get());
        }
        if (maxValueOpt.isPresent()) {
            query.setParameter("maxVal", maxValueOpt.get());
        }

        return query.getResultList();
    }

    public List<Product> showDeleted() {
        return entityManager.createQuery("select p from Product p where p.isDeleted==true")
                .getResultList();
    }

    @Transactional
    public void addProduct(String name, String categoryName, BigDecimal price, long amount, String imagePath) {
        List<Categories> categories = entityManager.createQuery("select c from Categories c where c.name=:name", Categories.class)
                .setParameter("name",categoryName)
                .getResultList();

        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Category has not been found");
        }

        Product newProduct = new Product(name,categories.getFirst(), price, amount, imagePath);
        entityManager.persist(newProduct);
    }

    @Transactional
    public void deleteProduct(String name) {
        List<Product> categories = entityManager.createQuery("select p from Product p where p.name=:name", Product.class)
                .setParameter("name", name)
                .getResultList();

        if (!categories.isEmpty()) {
            entityManager.remove(categories.getFirst());
        }
    }
}
