package com.fula.thymeleaf;

import com.fula.model.dto.SearchCustomerInfoDTO;
import com.fula.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

  @Autowired CustomerService customerService;

  @RequestMapping("/")
  public String toIndex() {
    return "index";
  }

  @RequestMapping("/test")
  public ModelAndView test(ModelAndView modelAndView) {
    modelAndView.addObject("name", "test");
    modelAndView.setViewName("test");
    return modelAndView;
  }

  @RequestMapping("/users")
  public ModelAndView users(ModelAndView modelAndView) {
    List<SearchCustomerInfoDTO> customerInfoList =
        customerService.search(new SearchCustomerInfoDTO());
    modelAndView.addObject("users", customerInfoList);
    modelAndView.setViewName("user");
    return modelAndView;
  }
}
