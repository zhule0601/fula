package com.fula.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerInfo {

  private Long id;
  private String name;
  private String roleId;
  private String lastRole;
  private String phone;
  private String email;
  private Integer sex;
  private Date gmtCreate;
  private Date gmtModified;
}
