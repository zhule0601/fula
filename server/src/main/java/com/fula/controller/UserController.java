package com.fula.controller;

import com.fula.mapper.system.ResultBody;
import com.fula.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/wx")
    public ResultBody<Boolean> user(@RequestParam String wxId) {
        userService.addWXUser(wxId);
        return new ResultBody<>(Boolean.TRUE);
    }

}
