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
 * Plain unit tests (no Spring context) for the autoconfigure-common beans and properties.
 */
class FF4JConfigurationUnitTest {

  @Test
  fun auditIsDisabledByDefault() {
    val configuration = FF4JConfiguration()

    assertThat(configuration.isAuditEnabled).isFalse
    assertThat(configuration.getFF4J().isEnableAudit).isFalse
  }

  @Test
  fun auditFlagIsAppliedToFF4j() {
    val configuration = FF4JConfiguration()
    configuration.isAuditEnabled = true

    assertThat(configuration.isAuditEnabled).isTrue
    assertThat(configuration.getFF4J().isEnableAudit).isTrue
  }

  @Test
  fun propertiesHaveDocumentedDefaults() {
    val properties = FF4JConfigurationProperties()

    assertThat(properties.api.contextPath).isEqualTo("/api/ff4j")
    assertThat(properties.api.springDoc.enabled).isFalse
    assertThat(properties.api.springDoc.group).isEqualTo("ff4j")
    assertThat(properties.audit.enabled).isFalse
    assertThat(properties.webConsole.enabled).isFalse
    assertThat(properties.webConsole.contextPath).isEqualTo("/ff4j-web-console")
  }

  @Test
  fun propertiesCanBeOverridden() {
    val properties = FF4JConfigurationProperties(
      api = FF4JConfigurationProperties.Api("/custom", FF4JConfigurationProperties.SpringDoc(true, "grp")),
      audit = FF4JConfigurationProperties.Audit(true),
      webConsole = FF4JConfigurationProperties.WebConsole(true, "/console")
    )

    assertThat(properties.api.contextPath).isEqualTo("/custom")
    assertThat(properties.api.springDoc.enabled).isTrue
    assertThat(properties.api.springDoc.group).isEqualTo("grp")
    assertThat(properties.audit.enabled).isTrue
    assertThat(properties.webConsole.enabled).isTrue
    assertThat(properties.webConsole.contextPath).isEqualTo("/console")
  }

  @Test
  fun openApiExcludesFF4jPathsWhenSpringDocDisabled() {
    val properties = FF4JConfigurationProperties(api = FF4JConfigurationProperties.Api("/custom"))
    val springDocProperties = SpringDocConfigProperties()

    FF4JOpenApiConfiguration(properties, springDocProperties)

    assertThat(springDocProperties.pathsToExclude).containsExactly("/custom/**")
  }

  @Test
  fun openApiLeavesPathsAloneWhenSpringDocEnabled() {
    val properties = FF4JConfigurationProperties(
      api = FF4JConfigurationProperties.Api("/custom", FF4JConfigurationProperties.SpringDoc(enabled = true))
    )
    val springDocProperties = SpringDocConfigProperties()
    val before = springDocProperties.pathsToExclude

    FF4JOpenApiConfiguration(properties, springDocProperties)

    assertThat(springDocProperties.pathsToExclude).isEqualTo(before)
  }

  @Test
  fun groupedOpenApiMatchesFF4jContextPath() {
    val properties = FF4JConfigurationProperties(
      api = FF4JConfigurationProperties.Api("/custom", FF4JConfigurationProperties.SpringDoc(true, "grp"))
    )
    val springDocProperties = SpringDocConfigProperties()

    val groupedOpenApi = FF4JOpenApiConfiguration(properties, springDocProperties).groupApiEnabled(springDocProperties)

    assertThat(groupedOpenApi.group).isEqualTo("grp")
    assertThat(groupedOpenApi.pathsToMatch).containsExactly("/custom/**")
  }
}
