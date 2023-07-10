# Spring boot starter for FF4J (Feature Flipping for Java)
![Build Status](https://github.com/ff4j/ff4j-spring-boot-starter-parent/actions/workflows/build_workflow.yml/badge.svg?branch=main)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e6fc893a940e44f18ade46d2d13036bf)](https://app.codacy.com/gh/ff4j/ff4j-spring-boot-starter-parent/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/ff4j/ff4j-spring-boot-starter-parent/branch/main/graph/badge.svg?token=P37n0ZwhlR)](https://codecov.io/gh/ff4j/ff4j-spring-boot-starter-parent)
[![License Apache2](http://img.shields.io/badge/license-APACHE2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Join the chat at https://gitter.im/paul58914080/ff4j-spring-boot-starter-parent](https://badges.gitter.im/paul58914080/ff4j-spring-boot-starter-parent.svg)](https://gitter.im/paul58914080/ff4j-spring-boot-starter-parent?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<p align="center">
<img src="https://github.com/ff4j/ff4j-spring-boot-starter-parent/blob/main/images/ff4j.png?raw=true" alt="Feature Flipping For Java" height="120px" />
<img src="https://github.com/ff4j/ff4j-spring-boot-starter-parent/blob/main/images/spring-boot.png?raw=true" alt="Spring boot" height="120px" />
</p>


This project aims in providing bootable starters with  RESTful apis for FF4J. We provide 2 starters, one for **webmvc** and another for **webflux**.
- [ff4j-spring-boot-starter-webmvc](#ff4j-spring-boot-starter-webmvc)
- [ff4j-spring-boot-starter-webflux](#ff4j-spring-boot-starter-webflux)

### ff4j-spring-boot-starter-webmvc

This starter is aimed to facilitate the use FF4J with spring boot webmvc. It provides the following features:
- Webapi's for FF4J
- OpenApi documentation for FF4J
- Web-console for FF4J

You can add the following dependency in your project to use this starter

```xml
<dependency>
  <groupId>org.ff4j</groupId>
  <artifactId>ff4j-spring-boot-starter-webmvc</artifactId>
  <version>2.0.0</version>
</dependency>
```

A sample project is located at [ff4j-spring-boot-starter-webmvc-sample](https://github.com/ff4j/ff4j-samples/tree/master/ff4j-spring-boot-samples/ff4j-spring-boot-starter-webmvc-sample)

### ff4j-spring-boot-starter-webflux

> **Disclaimer:** This starter is still in beta. Please use it with caution. Please note this is not fully supported but to lay a foundation for the future. 

This starter is aimed to facilitate the use FF4J with spring boot webflux.  It provides the following features:
- Webapi's for FF4J
- OpenApi documentation for FF4J

You can add the following dependency in your project to use this starter

```xml
<dependency>
  <groupId>org.ff4j</groupId>
  <artifactId>ff4j-spring-boot-starter-webflux</artifactId>
  <version>2.0.0</version>
</dependency>
```

A sample project is located at [ff4j-spring-boot-starter-webflux-sample](https://github.com/ff4j/ff4j-samples/tree/master/ff4j-spring-boot-samples/ff4j-spring-boot-starter-webflux-sample)

## About older version(version 1.x)

Please refer [branch 1.x](https://github.com/ff4j/ff4j-spring-boot-starter-parent/tree/1.x) for the older version. We will have limited support on 1.x version. We suggest you to move to the latest version.

## What is FF4J ?

FF4J is a proposition of [Feature Toggle](http://martinfowler.com/bliki/FeatureToggle.html). 
Features can be enabled or disabled through configuration at runtime with dedicated consoles, Web API, or __monitor__ features usage. The same web console can also define any __Property__ and change its value at runtime.

More information can be found at [ff4j.org](http://ff4j.org) or the [reference guide](https://github.com/clun/ff4j-extra/raw/master/ff4j-reference-guide-1.3.pdf). To access a demo please click [here] (http://cannys.com/ff4j-demo)
