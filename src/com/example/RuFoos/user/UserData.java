package com.example.RuFoos.user;

import com.example.RuFoos.domain.User;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface UserData {

        public int addUser(User user);
        public User getUserByUsername(String username);
}
