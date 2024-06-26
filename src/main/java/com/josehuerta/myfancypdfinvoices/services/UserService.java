package com.josehuerta.myfancypdfinvoices.services;

import com.josehuerta.myfancypdfinvoices.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserService {

    public User findById(String id) {
        String randomName = UUID.randomUUID().toString();
        // Always finds the user, every user has a random name
        return new User(id, randomName);
    }

}
