package com.example.RuFoos.user;

import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.User;

import java.util.List;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface UserService {

    public int addUser(User user);
    public User getUserByUsername(String username);
    public List<User> getAllUsers();
    public QuickMatch quickMatchSignUp(User user);
    public int updateUser(User User);
    public int getQuickMatchId();
    public QuickMatch leaveQuickMatch(User user);

}
