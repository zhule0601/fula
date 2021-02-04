package com.fula.controller;

import com.fula.model.dto.RegisterCustomerInfoDTO;
import com.fula.model.dto.SearchCustomerInfoDTO;
import com.fula.model.system.ResultBody;
import com.fula.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/v1")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResultBody<Boolean> register(@RequestBody RegisterCustomerInfoDTO registerCustomerInfoDTO) {
        Boolean result = customerService.register(registerCustomerInfoDTO);
        return new ResultBody<>(result);
    }

    @PostMapping("/search")
    public ResultBody<List<SearchCustomerInfoDTO>> search(SearchCustomerInfoDTO searchCustomerInfoDTO) {
        List<SearchCustomerInfoDTO> result = customerService.search(searchCustomerInfoDTO);
        return new ResultBody<>(result);
    }

}
