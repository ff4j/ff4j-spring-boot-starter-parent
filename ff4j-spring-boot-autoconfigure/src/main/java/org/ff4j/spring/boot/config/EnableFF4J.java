package org.ff4j.spring.boot.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({FF4JConfiguration.class})
public @interface EnableFF4J {
}
