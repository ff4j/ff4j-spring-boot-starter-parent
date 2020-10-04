package org.ff4j.sample.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(value = "greeting", tags = "operations pertaining to greeting")
public class Greeting {

  @GetMapping()
  @ApiOperation(value = "Get a greeting")
  @ApiResponses({@ApiResponse(code = 200, message = "got a greeting message")})
  public String sayHello() {
    return "Hello world";
  }
}
