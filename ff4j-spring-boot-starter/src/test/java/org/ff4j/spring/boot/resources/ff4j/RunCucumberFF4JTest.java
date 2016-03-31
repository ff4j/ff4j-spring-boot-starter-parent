package org.ff4j.spring.boot.resources.ff4j;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/FF4JResource.feature", strict = true,
        plugin = {"json:target/cucumber/FF4JResource.json", "junit:target/cucumber/FF4JResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/ff4j", tags = "@FF4JResource")
public class RunCucumberFF4JTest {
}
