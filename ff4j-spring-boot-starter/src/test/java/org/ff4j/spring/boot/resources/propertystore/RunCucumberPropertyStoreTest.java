package org.ff4j.spring.boot.resources.propertystore;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/PropertyStoreResource.feature", strict = true,
        plugin = {"json:target/cucumber/PropertyStoreResource.json", "junit:target/cucumber/PropertyStoreResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/propertystore", tags = "@PropertyStoreResource")
public class RunCucumberPropertyStoreTest {
}
