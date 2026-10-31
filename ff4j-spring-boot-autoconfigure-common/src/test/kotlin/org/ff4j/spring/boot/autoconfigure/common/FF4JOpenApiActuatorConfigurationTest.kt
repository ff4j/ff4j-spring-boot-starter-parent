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

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

/**
 * Reproduces GH issue: with `springdoc.show-actuator=true` and a management port
 * different from the server port, springdoc registers a BeanFactoryPostProcessor
 * depending on all GroupedOpenApi beans, forcing early instantiation of
 * FF4JOpenApiConfiguration before autowiring infrastructure is ready.
 */
@SpringBootTest(classes = [Application::class])
@TestPropertySource(
  properties = [
    "ff4j.api.spring-doc.enabled=true",
    "ff4j.api.spring-doc.group=ff4j-custom-group",
    "springdoc.show-actuator=true",
    "server.port=8080",
    "management.server.port=9090"
  ]
)
class FF4JOpenApiActuatorConfigurationTest {

  @Autowired
  private lateinit var groupedOpenApi: GroupedOpenApi

  @Test
  fun testBootWithActuatorOnDifferentPort() {
    Assertions.assertThat(groupedOpenApi.group).isEqualTo("ff4j-custom-group")
  }
}
