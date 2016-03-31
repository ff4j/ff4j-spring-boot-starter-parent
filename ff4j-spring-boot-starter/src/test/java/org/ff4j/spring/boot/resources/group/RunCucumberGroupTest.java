package org.ff4j.spring.boot.resources.group;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/GroupResource.feature", strict = true,
        plugin = {"json:target/cucumber/GroupResource.json", "junit:target/cucumber/GroupResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/group", tags = "@GroupResource")
public class RunCucumberGroupTest {
}
