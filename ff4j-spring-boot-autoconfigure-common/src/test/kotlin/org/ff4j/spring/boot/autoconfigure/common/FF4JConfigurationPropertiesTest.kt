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

/**
 * The defaults asserted here are part of the starter's published contract: downstream applications
 * that set no `ff4j.*` property at all get exactly these values.
 */
class FF4JConfigurationPropertiesTest {

  @Test
  fun defaultsAreTheDocumentedOnes() {
    val properties = FF4JConfigurationProperties()

    assertThat(properties.api.contextPath).isEqualTo("/api/ff4j")
    assertThat(properties.api.springDoc.enabled).isFalse()
    assertThat(properties.api.springDoc.group).isEqualTo("ff4j")
    assertThat(properties.audit.enabled).isFalse()
    assertThat(properties.webConsole.enabled).isFalse()
    assertThat(properties.webConsole.contextPath).isEqualTo("/ff4j-web-console")
  }

  @Test
  fun explicitlyBoundValuesAreRetained() {
    val properties = FF4JConfigurationProperties(
      api = FF4JConfigurationProperties.Api(
        contextPath = "/custom/ff4j",
        springDoc = FF4JConfigurationProperties.SpringDoc(enabled = true, group = "custom-group")
      ),
      audit = FF4JConfigurationProperties.Audit(enabled = true),
      webConsole = FF4JConfigurationProperties.WebConsole(enabled = true, contextPath = "/custom-console")
    )

    assertThat(properties.api.contextPath).isEqualTo("/custom/ff4j")
    assertThat(properties.api.springDoc.enabled).isTrue()
    assertThat(properties.api.springDoc.group).isEqualTo("custom-group")
    assertThat(properties.audit.enabled).isTrue()
    assertThat(properties.webConsole.enabled).isTrue()
    assertThat(properties.webConsole.contextPath).isEqualTo("/custom-console")
  }

  @Test
  fun mutatingANestedPropertyIsVisibleThroughTheParent() {
    val properties = FF4JConfigurationProperties()

    properties.api.springDoc.enabled = true
    properties.api.contextPath = "/mutated"

    assertThat(properties.api.springDoc.enabled).isTrue()
    assertThat(properties.api.contextPath).isEqualTo("/mutated")
  }
}
