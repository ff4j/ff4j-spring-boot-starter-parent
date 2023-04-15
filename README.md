<p align="center">
<img src="https://github.com/paul58914080/ff4j-spring-boot-starter-parent/blob/master/images/ff4j.png" alt="Feature Flipping For Java" height="120px" />
<img src="https://github.com/paul58914080/ff4j-spring-boot-starter-parent/blob/master/images/spring-boot.png" alt="Spring boot" height="120px" />
</p>

## Spring boot starter for FF4J (Feature Flipping for Java)

> This is a **work-in-progress** branch (master). Please refer branch [v1](https://github.com/ff4j/ff4j-spring-boot-starter-parent/tree/v1) for the current version's documentation

![Build Status](https://github.com/ff4j/ff4j-spring-boot-starter/actions/workflows/build_workflow.yml/badge.svg?branch=main)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e6fc893a940e44f18ade46d2d13036bf)](https://app.codacy.com/gh/ff4j/ff4j-spring-boot-starter-parent/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade) 
[![codecov](https://codecov.io/gh/ff4j/ff4j-spring-boot-starter-parent/branch/main/graph/badge.svg?token=P37n0ZwhlR)](https://codecov.io/gh/ff4j/ff4j-spring-boot-starter-parent) 
[![License Apache2](http://img.shields.io/badge/license-APACHE2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) 
[![Join the chat at https://gitter.im/paul58914080/ff4j-spring-boot-starter-parent](https://badges.gitter.im/paul58914080/ff4j-spring-boot-starter-parent.svg)](https://gitter.im/paul58914080/ff4j-spring-boot-starter-parent?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) 

[Swagger Documentation](https://ff4j.herokuapp.com/swagger-ui.html)

This project aims in providing a bootable starter which provides RESTful apis for FF4J. 

Create a bootable jar with 

`mvn clean install`

Add dependency in your project

### webmvc

```xml
<dependency>
  <groupId>org.ff4j</groupId>
  <artifactId>ff4j-spring-boot-starter-webmvc</artifactId>
  <version>2.0.0</version>
</dependency>
```

### webflux

```xml
<dependency>
  <groupId>org.ff4j</groupId>
  <artifactId>ff4j-spring-boot-starter-webflux</artifactId>
  <version>2.0.0</version>
</dependency>
```

### Sample

A sample project is located at [ff4j-spring-boot-sample](https://github.com/paul58914080/ff4j-spring-boot-starter-parent/tree/master/ff4j-spring-boot-sample)

Use `mvn spring-boot:run`

Once the sample application is booted use the following curl command:
 
`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/ff4j`

Have a look at [FF4JConfiguration](https://github.com/paul58914080/ff4j-spring-boot-starter-parent/blob/master/ff4j-spring-boot-sample/src/main/java/org/ff4j/sample/config/FF4JConfiguration.java)

### What is FF4J ?

FF4J is a proposition of [Feature Toggle](http://martinfowler.com/bliki/FeatureToggle.html). 
Features can be enabled or disabled through configuration at runtime with dedicated consoles, Web API, or __monitor__ features usage. The same web console can also define any __Property__ and change its value at runtime.

More information can be found at [ff4j.org](http://ff4j.org) or the [reference guide](https://github.com/clun/ff4j-extra/raw/master/ff4j-reference-guide-1.3.pdf). To access a demo please click [here] (http://cannys.com/ff4j-demo)

 <p align="center">
  <img src="https://raw.github.com/clun/ff4j/master/src/site/resources/images/ff4j-console.png?raw=true" />
  <br>
  <img src="https://raw.github.com/clun/ff4j/master/src/site/resources/images/stack3.png?raw=true" />
</p>
</p>
