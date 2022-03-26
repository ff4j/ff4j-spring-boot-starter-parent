package org.ff4j.sample.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Greeting {

  @GetMapping()
  public String sayHello() {
    return "Hello world";
  }
}
