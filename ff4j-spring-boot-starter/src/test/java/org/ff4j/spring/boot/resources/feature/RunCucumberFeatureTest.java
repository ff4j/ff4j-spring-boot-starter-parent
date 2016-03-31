package org.ff4j.spring.boot.resources.feature;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/FeatureResource.feature", strict = true,
        plugin = {"json:target/cucumber/FeatureResource.json", "junit:target/cucumber/FeatureResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/feature", tags = "@FeatureResource")
public class RunCucumberFeatureTest {
}
