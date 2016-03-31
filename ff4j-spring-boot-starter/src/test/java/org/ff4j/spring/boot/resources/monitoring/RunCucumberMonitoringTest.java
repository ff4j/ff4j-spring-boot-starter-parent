package org.ff4j.spring.boot.resources.monitoring;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/MonitoringResource.feature", strict = true,
        plugin = {"json:target/cucumber/MonitoringResource.json", "junit:target/cucumber/MonitoringResource.xml"},
        glue = "classpath:org/ff4j/spring/boot/resources/monitoring", tags = "@MonitoringResource")
public class RunCucumberMonitoringTest {
}
