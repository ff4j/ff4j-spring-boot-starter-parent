package org.ff4j.spring.boot;

import org.ff4j.spring.boot.config.EnableFF4J;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableFF4J
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
