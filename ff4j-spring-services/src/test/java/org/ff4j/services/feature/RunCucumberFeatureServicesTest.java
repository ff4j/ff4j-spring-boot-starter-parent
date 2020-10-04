package org.ff4j.services.feature;

/*
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2016 FF4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/FeatureServices.feature", strict = true,
    plugin = {"json:target/cucumber/FeatureService.json",
        "junit:target/cucumber/FeatureService.xml"},
    glue = "classpath:org/ff4j/services/feature", tags = "@FeatureServices")
public class RunCucumberFeatureServicesTest {

}
