package org.ff4j.services.feature

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber::class)
@CucumberOptions(
        features = ["classpath:features/FeatureServices.feature"],
        strict = true,
        plugin = ["json:target/cucumber/FeatureService.json", "junit:target/cucumber/FeatureService.xml"],
        glue = ["classpath:org.ff4j.services.feature"],
        tags = ["@FeatureServices", "~@Ignore"]
)
class RunFeatureTest
