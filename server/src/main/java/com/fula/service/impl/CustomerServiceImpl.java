package com.fula.service.impl;

import com.fula.mapper.CustomerInfoDao;
import com.fula.mapper.CustomerLoginDao;
import com.fula.model.CustomerInfo;
import com.fula.model.CustomerLogin;
import com.fula.model.dto.RegisterCustomerInfoDTO;
import com.fula.model.dto.SearchCustomerInfoDTO;
import com.fula.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerInfoDao customerInfoDao;
  private final CustomerLoginDao customerLoginDao;

  public CustomerServiceImpl(CustomerInfoDao customerInfoDao, CustomerLoginDao customerLoginDao) {
    this.customerInfoDao = customerInfoDao;
    this.customerLoginDao = customerLoginDao;
  }

  @Transactional
  @Override
  public Boolean register(RegisterCustomerInfoDTO registerCustomerInfoDTO) {
    // 注册基本信息
    CustomerInfo customerInfo = new CustomerInfo();
    BeanUtils.copyProperties(registerCustomerInfoDTO, customerInfo);
    // 赋予默认角色
    customerInfo.setRoleId("1");
    customerInfo.setLastRole("1");
    customerInfoDao.insert(customerInfo);

    // 登录信息填写
    CustomerLogin customerLogin = new CustomerLogin();
    customerLogin.setCustomerInfoId(customerInfo.getId());
    customerLogin.setPassword(registerCustomerInfoDTO.getPassword());
    customerLogin.setLoginName(registerCustomerInfoDTO.getName());
    customerLogin.setState(1);
    customerLoginDao.insert(customerLogin);

    return Boolean.TRUE;
  }

  @Override
  public List<SearchCustomerInfoDTO> search(SearchCustomerInfoDTO searchCustomerInfoDTO) {
    // 查询基本信息
    CustomerInfo param = new CustomerInfo();
    BeanUtils.copyProperties(searchCustomerInfoDTO, param);
    List<CustomerInfo> customerInfoList = customerInfoDao.selectBySelective(param);
    return customerInfoList.stream()
        .map(
            cl -> {
              SearchCustomerInfoDTO temp = new SearchCustomerInfoDTO();
              BeanUtils.copyProperties(cl, temp);
              return temp;
            })
        .collect(Collectors.toList());
  }
}
