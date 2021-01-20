package com.fula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zl
 * @description springboot 启动类
 * @date 2020/8/28 23:27
 */
@Configuration
@SpringBootApplication
@EnableScheduling
public class FULAApplication {

  public static void main(String[] args) {
    SpringApplication.run(FULAApplication.class, args);
  }

  //  public static void main(String[] args) {
  //    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test.xml");
  //    Assert.assertEquals(((User) applicationContext.getBean("user")).getWxUid(), "7890");
  //  }
  // test.xml 放到 resource 下面, 用于跟踪 spring 启动过程
  // <?xml version="1.0" encoding="UTF-8"?>
  // <beans xmlns="http://www.springframework.org/schema/beans"
  //       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  //       xsi:schemaLocation="http://www.springframework.org/schema/beans
  // http://www.springframework.org/schema/beans/spring-beans.xsd">
  //
  //    <bean name="user" class="com.fula.model.User">
  //        <property name="wxUid" value="789"/>
  //    </bean>
  // </beans>

}
