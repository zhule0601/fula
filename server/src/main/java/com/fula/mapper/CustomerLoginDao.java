package com.fula.mapper;

import com.fula.model.CustomerLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CustomerLoginDao {
    int insert(CustomerLogin record);

    int insertSelective(CustomerLogin record);
}
