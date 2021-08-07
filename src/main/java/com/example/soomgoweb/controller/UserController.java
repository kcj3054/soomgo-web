package com.example.soomgoweb.controller;

import com.example.soomgoweb.model.CreateUserRequset;
import com.example.soomgoweb.model.GetUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  // properties 파일(application.yml)에 세팅한 내용을 Spring 변수에 주입하는 역할을 한다.
  // 속성 값은 런타임에 변수로 주입되며 속성값이 없으면 오류
  //${~}값을 읽어와 읽어와서 apiHost로 쓸게
  @Value("${service.soomgo-api.host}")
  private String apiHost;

  private final Environment env;
  private final ObjectMapper objectMapper;

  @PostMapping("/create-user")
  @ResponseBody
  public void createUser(@RequestBody CreateUserRequset request) {

    RestTemplate restTemplate = new RestTemplate();

    //서버 측에 리소스를 생성하거나 수정하는 기능을 구현할 수 있다
    ResponseEntity<Void> voidResponseEntity = restTemplate.postForEntity(apiHost + "/users", request, Void.class);
    HttpStatus statusCode = voidResponseEntity.getStatusCode();
    log.info("statusCode : {}", statusCode);
  }

  @GetMapping("/list")
  public String userList(Model model) {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> forEntity = restTemplate.getForEntity(apiHost + "/users", String.class);
    try {
      List<GetUserResponse> getUserResponses = objectMapper.readValue(forEntity.getBody(), new TypeReference<List<GetUserResponse>>() {
      });
      model.addAttribute("list", getUserResponses);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return "/user/list";
  }

}
