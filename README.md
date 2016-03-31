<p align="center">
<img src="https://github.com/paul58914080/ff4j-spring-boot-starter-parent/blob/master/images/spring-boot.png" alt="Spring boot" height="120px" />
<img src="https://github.com/paul58914080/ff4j-spring-boot-starter-parent/blob/master/images/ff4j.png" alt="Feature Flipping For Java" height="120px" />
</p>

## Spring boot starter for FF4J (Feature Flipping for Java)

[![Join the chat at https://gitter.im/paul58914080/ff4j-spring-boot-starter-parent](https://badges.gitter.im/paul58914080/ff4j-spring-boot-starter-parent.svg)](https://gitter.im/paul58914080/ff4j-spring-boot-starter-parent?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://travis-ci.org/paul58914080/ff4j-spring-boot-starter-parent.svg?branch=master)](https://travis-ci.org/paul58914080/ff4j-spring-boot-starter-parent) [![Coverage Status](https://coveralls.io/repos/github/paul58914080/ff4j-spring-boot-starter-parent/badge.svg?branch=master)](https://coveralls.io/github/paul58914080/ff4j-spring-boot-starter-parent?branch=master)

This project aims in providing a bootable starter which provides RESTful apis for FF4J. 

Create a bootable jar with 

`mvn clean install`

Add dependency in your project

~~~
<dependency>
	<groupId>org.ff4j</groupId>
	<artifactId>ff4j-spring-boot-starter</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
~~~

All you would have to do in your configuration is to enable ff4j with `@EnableFF4J`

### Sample

A sample project can be found at [ff4j-spring-boot-sample](https://github.com/paul58914080/ff4j-spring-boot-starter-parent/tree/master/ff4j-spring-boot-sample)

Use `mvn spring-boot:run`

Once the sample application is booted use the following curl command
 
`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/ff4j`

Have a look at [FF4JConfiguration](https://github.com/paul58914080/ff4j-spring-boot-starter-parent/blob/master/ff4j-spring-boot-sample/src/main/java/org/ff4j/sample/config/FF4JConfiguration.java)

### What is FF4J ?

FF4J is a proposition of [Feature Toggle](http://martinfowler.com/bliki/FeatureToggle.html). 
You can enable and disable features through configuration at runtime with dedicated consoles or Web API but also __monitor__ features usage. You can also define any __Property__ and change its value at runtime with 
the exact same web console.

More information at [ff4j.org](http://ff4j.org) or [reference guide](https://github.com/clun/ff4j-extra/raw/master/ff4j-reference-guide-1.3.pdf). To access a demo please click [here] (http://cannys.com/ff4j-demo)

 <p align="center">
  <img src="https://raw.github.com/clun/ff4j/master/src/site/resources/images/ff4j-console.png?raw=true" />
  <br>
  <img src="https://raw.github.com/clun/ff4j/master/src/site/resources/images/stack3.png?raw=true" />
</p>


</p>
