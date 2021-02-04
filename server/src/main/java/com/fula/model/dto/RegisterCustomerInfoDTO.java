package com.fula.model.dto;

import lombok.Data;

@Data
public class RegisterCustomerInfoDTO {

  private String name;
  private String phone;
  private String email;
  private Integer sex;
  private String password;
}
