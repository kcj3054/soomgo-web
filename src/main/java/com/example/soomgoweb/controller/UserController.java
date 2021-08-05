package com.example.soomgoweb.controller;

import com.example.soomgoweb.model.CreateUserRequset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

  @Value("${service.soomgo-api.host}")
  private String apiHost;

  @PostMapping("/create-user")
  @ResponseBody
  public void createUser(@RequestBody CreateUserRequset request) {

    RestTemplate restTemplate = new RestTemplate();

    //서버 측에 리소스를 생성하거나 수정하는 기능을 구현할 수 있다
    ResponseEntity<Void> voidResponseEntity = restTemplate.postForEntity(apiHost + "/users", request, Void.class);
    HttpStatus statusCode = voidResponseEntity.getStatusCode();
    log.info("statusCode : {}", statusCode);
  }

}
