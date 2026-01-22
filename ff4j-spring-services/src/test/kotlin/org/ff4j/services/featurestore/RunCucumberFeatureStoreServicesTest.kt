/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2026 FF4J
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
package org.ff4j.services.featurestore

import io.cucumber.junit.platform.engine.Constants
import io.cucumber.spring.CucumberContextConfiguration
import org.ff4j.services.config.FF4JTestConfiguration
import org.junit.platform.suite.api.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/FeatureStoreServices.feature")
@ConfigurationParameters(
  ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "org.ff4j.services.featurestore"
  ),
  ConfigurationParameter(
    key = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@FeatureStoreServices"
  ),
  ConfigurationParameter(
    key = Constants.JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME,
    value = "long"
  ),
  ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true"),
  ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "json:target/cucumber/FeatureStoreServices.json"
  )
)
class RunCucumberFeatureStoreServicesTest

@CucumberContextConfiguration
@ContextConfiguration(classes = [FF4JTestConfiguration::class])
@ActiveProfiles("test")
class SpringCucumberTestConfig
