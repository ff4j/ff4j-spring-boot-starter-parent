package org.ff4j.spring.boot.resources.featurestore;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/FeatureStoreResource.feature", strict = true,
        plugin = {"json:target/cucumber/FeatureStoreResource.json", "junit:target/cucumber/FeatureStoreResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/featurestore", tags = "@FeatureStoreResource")
public class RunCucumberFeatureStoreTest {
}
