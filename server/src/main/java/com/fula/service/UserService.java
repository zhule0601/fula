package com.fula.service;

import com.fula.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    int addWXUser(String wxId);

    int addUser(User user);

    List<User> userList();
}
