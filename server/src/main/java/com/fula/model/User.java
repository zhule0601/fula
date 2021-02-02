package com.fula.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

  private Long id;
  private Date gmtCreate;
  private Date gmtModified;
  private String wxUid;
  private String password;
  private String state;
}
