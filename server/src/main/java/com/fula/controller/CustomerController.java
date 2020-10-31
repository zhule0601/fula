package com.fula.controller;

import com.fula.model.dto.RegisterCustomerInfoDTO;
import com.fula.model.system.ResultBody;
import com.fula.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResultBody<Boolean> user(@RequestBody RegisterCustomerInfoDTO registerCustomerInfoDTO) {
        Boolean result = customerService.register(registerCustomerInfoDTO);
        return new ResultBody<>(result);
    }

}
