package com.example.RuFoos.user;

import com.example.RuFoos.domain.User;

import java.util.List;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface UserService {

        public int addUser(User user);
        public User getUserByUsername(String username);
        public List<User> getAllUsers();
}