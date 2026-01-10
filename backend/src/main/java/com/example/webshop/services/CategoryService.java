package com.example.webshop.services;

import com.example.webshop.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @PersistenceContext
    EntityManager entityManager;

    public CategoryService() {
    }

    public Category getCategoryById(UUID id) {
        List<Category> categories = entityManager.createQuery("select c from Categories c where c.id=:id", Category.class)
                .setParameter("id", id)
                .getResultList();

        if (categories.isEmpty()) {
            return null;
        }

        return categories.getFirst();
    }

    private boolean categoryExists(String name) {
        List<Category> categories = entityManager.createQuery("select c from Categories c where c.name=:name", Category.class)
                .setParameter("name",name)
                .getResultList();

        return !categories.isEmpty();
    }

    public Category getCategoryIfExists(String name) {
        if (categoryExists(name)) {
            List<Category> categories = entityManager.createQuery("select c from Categories c where c.name=:name", Category.class)
                    .setParameter("name",name)
                    .getResultList();

            return categories.getFirst();
        }

        return null;
    }

    @Transactional
    public void createCategory(String name) {
        if (categoryExists(name)) {
            List<Category> categories = entityManager.createQuery("select c from Categories c where c.name=:name", Category.class)
                    .setParameter("name",name)
                    .getResultList();

            return categories.getFirst();
        }

        return null;
    }

    @Transactional
    public Category createCategory(String name) {
        if (categoryExists(name)) {
            return null;
        }

        Category category = new Category(name);
        entityManager.persist(category);

        return category;
    }
}
