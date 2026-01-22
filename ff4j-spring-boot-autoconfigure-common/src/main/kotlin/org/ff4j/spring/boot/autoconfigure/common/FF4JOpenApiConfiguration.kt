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

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.core.configuration.SpringDocConfiguration
import org.springdoc.core.models.GroupedOpenApi
import org.springdoc.core.properties.SpringDocConfigProperties
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean

@AutoConfiguration
@AutoConfigureAfter(FF4JConfiguration::class, SpringDocConfiguration::class)
@ConditionalOnClass(SpringDocConfiguration::class)
class FF4JOpenApiConfiguration(
  private val config: FF4JConfigurationProperties,
  private val springDocConfigProperties: SpringDocConfigProperties = SpringDocConfigProperties()
) {

  private val log: Logger = LoggerFactory.getLogger(FF4JOpenApiConfiguration::class.java)

  init {
    if (!config.api.springDoc.enabled) {
      log.info("Exclude FF4J OpenAPI configuration")
      springDocConfigProperties.pathsToExclude = listOf("${config.api.contextPath}/**")
    }
  }

  @Bean
  @ConditionalOnProperty(name = ["ff4j.api.spring-doc.enabled"], havingValue = "true", matchIfMissing = false)
  fun groupApiEnabled(springDocConfigProperties: SpringDocConfigProperties): GroupedOpenApi {
    log.info("Initializing FF4J GroupedOpenApi configuration")
    return GroupedOpenApi.builder().group(config.api.springDoc.group).pathsToMatch("${config.api.contextPath}/**")
      .build()
  }
}
