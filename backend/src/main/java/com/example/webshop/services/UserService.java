package com.example.webshop.services;

import com.example.webshop.DTO.UserRegistrationDTO;
import com.example.webshop.DTO.UserUpdateDTO;
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
        return entityManager.createQuery("from Users", User.class).getResultList();
    }

    public User getUserByLogin(String login) {
        List<User> users = entityManager.createQuery("select u from Users u where u.login=:login",User.class)
                .setParameter("login", login)
                .getResultList();

        return !users.isEmpty() ? users.getFirst() : null;
    }

    @Transactional
    public User addUser(UserRegistrationDTO dto){
        var possibleUser = getUserByLogin(dto.login());

        if (possibleUser == null) {
            var newUser = new User(dto.firstname(),
                    dto.lastname(),
                    dto.country(),
                    dto.city(),
                    dto.street(),
                    dto.postalNumber(),
                    dto.phoneNumber(),
                    dto.mail(),
                    dto.login(),
                    dto.password());

            entityManager.persist(newUser);
            return newUser;
        }

        return null;
    }

    public User updateUser(UserUpdateDTO dto) {
        User user = getUserByLogin(dto.login());

        if (user == null) {
            return null;
        }
        if (dto.firstname() != null) {
            user.setFirstname(dto.firstname());
        }
        if (dto.lastname() != null) {
            user.setLastname(dto.lastname());
        }
        if (dto.phoneNumber() != null) {
            user.setPhoneNumber(dto.phoneNumber());
        }

        if (dto.country() != null) {
            user.setCountry(dto.country());
        }
        if (dto.city() != null) {
            user.setCity(dto.city());
        }
        if (dto.street() != null) {
            user.setStreet(dto.street());
        }
        if (dto.postalNumber() != null) {
            user.setPostalNumber(dto.postalNumber());
        }

        if (dto.mail() != null) {
            user.setMail(dto.mail());
        }
        if (dto.login() != null) {
            user.setLogin(dto.login());
        }

        if (dto.password() != null) {
            user.setPasswordHash(dto.password());
        }

        return user;
    }
}