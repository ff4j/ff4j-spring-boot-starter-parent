/*-
 * #%L
 * ff4j-spring-boot-autoconfigure-common
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
package org.ff4j.spring.boot.autoconfigure.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springdoc.core.properties.SpringDocConfigProperties

/**
 * The FF4J API is hidden from springdoc unless `ff4j.api.spring-doc.enabled` is set — asserted here
 * on the actual [SpringDocConfigProperties] the configuration mutates, in both states, since that
 * exclusion is what keeps the FF4J endpoints out of a consumer's published OpenAPI document.
 */
class FF4JOpenApiConfigurationTest {

  private fun propertiesWith(springDocEnabled: Boolean, contextPath: String = "/api/ff4j", group: String = "ff4j") =
    FF4JConfigurationProperties(
      api = FF4JConfigurationProperties.Api(
        contextPath = contextPath,
        springDoc = FF4JConfigurationProperties.SpringDoc(enabled = springDocEnabled, group = group)
      )
    )

  @Test
  fun theFF4JApiPathsAreExcludedFromSpringDocWhenTheIntegrationIsDisabled() {
    val springDocConfigProperties = SpringDocConfigProperties()

    FF4JOpenApiConfiguration(propertiesWith(springDocEnabled = false), springDocConfigProperties)

    assertThat(springDocConfigProperties.pathsToExclude).containsExactly("/api/ff4j/**")
  }

  @Test
  fun theExclusionFollowsTheConfiguredContextPath() {
    val springDocConfigProperties = SpringDocConfigProperties()

    FF4JOpenApiConfiguration(
      propertiesWith(springDocEnabled = false, contextPath = "/custom/ff4j"),
      springDocConfigProperties
    )

    assertThat(springDocConfigProperties.pathsToExclude).containsExactly("/custom/ff4j/**")
  }

  @Test
  fun noPathIsExcludedWhenTheSpringDocIntegrationIsEnabled() {
    val springDocConfigProperties = SpringDocConfigProperties()

    FF4JOpenApiConfiguration(propertiesWith(springDocEnabled = true), springDocConfigProperties)

    assertThat(springDocConfigProperties.pathsToExclude).isNullOrEmpty()
  }

  @Test
  fun theGroupedOpenApiMatchesTheConfiguredGroupAndContextPath() {
    val configuration = FF4JOpenApiConfiguration(
      propertiesWith(springDocEnabled = true, contextPath = "/custom/ff4j", group = "custom-group"),
      SpringDocConfigProperties()
    )

    val groupedOpenApi = configuration.groupApiEnabled(SpringDocConfigProperties())

    assertThat(groupedOpenApi.group).isEqualTo("custom-group")
    assertThat(groupedOpenApi.pathsToMatch).containsExactly("/custom/ff4j/**")
  }
}
