package org.ff4j.spring.boot.resources.property;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/PropertyResource.feature", strict = true,
        plugin = {"json:target/cucumber/PropertyResource.json", "junit:target/cucumber/PropertyResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/property", tags = "@PropertyResource")
public class RunCucumberPropertyTest {
}
