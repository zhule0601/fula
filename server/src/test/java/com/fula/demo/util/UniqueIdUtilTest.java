package com.fula.demo.util;

import org.testng.annotations.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class UniqueIdUtilTest {

  private List<String> idList;

  @BeforeTest
  public void before() {
    idList = new Vector<>();
  }

  @Test(invocationCount = 100, threadPoolSize = 20)
  public void testGenerator() {
    String id = UniqueIdUtil.getUniqueId();
    // System.out.println(id);
    idList.add(id);
  }

  @AfterTest
  public void afterMethod() {
    System.out.println("idList: " + idList.size());
    List<String> uniqueIdList = idList.stream().distinct().collect(Collectors.toList());
    System.out.println("uniqueIdList: " + uniqueIdList.size());
    assertEquals(uniqueIdList.size(), idList.size());
    System.out.println(idList.get(0));
    System.out.println(idList.get(1));
    System.out.println(idList.get(2));
  }
}
