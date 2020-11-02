package com.fula.service.impl;

import com.fula.mapper.CustomerInfoDao;
import com.fula.mapper.CustomerLoginDao;
import com.fula.model.CustomerInfo;
import com.fula.model.CustomerLogin;
import com.fula.model.dto.RegisterCustomerInfoDTO;
import com.fula.model.dto.SearchCustomerInfoDTO;
import com.fula.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerInfoDao customerInfoDao;

    @Autowired
    CustomerLoginDao customerLoginDao;


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
        List<SearchCustomerInfoDTO> result = customerInfoList.stream().map(cl -> {
            SearchCustomerInfoDTO temp = new SearchCustomerInfoDTO();
            BeanUtils.copyProperties(cl, temp);
            return temp;
        }).collect(Collectors.toList());
        return result;
    }
}
