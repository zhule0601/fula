package com.fula.service;

import com.fula.model.dto.RegisterCustomerInfoDTO;
import com.fula.model.dto.SearchCustomerInfoDTO;

import java.util.List;

public interface CustomerService {

    Boolean register(RegisterCustomerInfoDTO registerCustomerInfoDTO);

    List<SearchCustomerInfoDTO> search(SearchCustomerInfoDTO searchCustomerInfoDTO);
}
