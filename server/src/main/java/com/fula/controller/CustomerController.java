package com.fula.controller;

import com.fula.model.dto.RegisterCustomerInfoDTO;
import com.fula.model.dto.SearchCustomerInfoDTO;
import com.fula.model.system.ResultBody;
import com.fula.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResultBody<Boolean> register(@RequestBody RegisterCustomerInfoDTO registerCustomerInfoDTO) {
        // todo 注册前的检查工作(参数, uk等)
        Boolean result = customerService.register(registerCustomerInfoDTO);
        return new ResultBody<>(result);
    }

    @PostMapping("/search")
    public ResultBody<List<SearchCustomerInfoDTO>> search(SearchCustomerInfoDTO searchCustomerInfoDTO) {
        List<SearchCustomerInfoDTO> result = customerService.search(searchCustomerInfoDTO);
        return new ResultBody<>(result);
    }

}
