/*-
 * #%L
 * ff4j-spring-rest-api
 * %%
 * Copyright (C) 2013 - 2023 FF4J
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
package org.ff4j.spring.rest.api.resources.property

import io.cucumber.junit.platform.engine.Constants
import io.cucumber.spring.CucumberContextConfiguration
import org.ff4j.spring.rest.api.RestTestApplication
import org.ff4j.spring.rest.api.config.FF4JTestConfiguration
import org.junit.platform.suite.api.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/PropertyResource.feature")
@ConfigurationParameters(
  ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "org.ff4j.spring.rest.api.resources.property"
  ),
  ConfigurationParameter(
    key = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@PropertyResource"
  ),
  ConfigurationParameter(
    key = Constants.JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME,
    value = "long"
  ),
  ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true"),
  ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "json:target/cucumber/PropertyResource.json"
  )
)
class RunCucumberPropertyTest

@CucumberContextConfiguration
@ContextConfiguration(classes = [FF4JTestConfiguration::class, RestTestApplication::class])
@WebAppConfiguration
@ActiveProfiles("test")
class SpringCucumberTestConfig
