package com.example.webshop.services;

import com.example.webshop.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public User getUserByLogin(String login) {
        return entityManager.find(User.class, login);
    }

    @Transactional
    public void addUser(User user){ //TODO: Refactor
        entityManager.persist(user);
    }
}