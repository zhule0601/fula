package com.fula.service;

import com.fula.model.User;
import org.springframework.stereotype.Service;


public interface UserService {

    int addWXUser(String wxId);

    int addUser(User user);
}
