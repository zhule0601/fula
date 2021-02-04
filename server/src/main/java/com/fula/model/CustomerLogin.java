package com.fula.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerLogin implements Serializable {
    private Long customerInfoId;
    private String loginName;
    private String password;
    private Integer state;
    private Date gmtCreate;
    private Date gmtModified;

}