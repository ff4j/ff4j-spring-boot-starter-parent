package org.ff4j.spring.boot.config;

import org.ff4j.FF4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Configuration
@ConditionalOnClass({FF4j.class})
@ComponentScan(value = "org.ff4j.spring.boot")
public class FF4JConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FF4j getFF4j() {
        return new FF4j();
    }
}
