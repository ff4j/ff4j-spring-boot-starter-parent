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
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment

/**
 * This configuration must remain instantiable with a default constructor and must not rely on
 * autowired state: when `springdoc.show-actuator=true` and the management port differs from the
 * server port, springdoc registers a BeanFactoryPostProcessor depending on all [GroupedOpenApi]
 * beans, so this class is instantiated before autowiring and configuration-properties binding
 * are available.
 */
@AutoConfiguration
@AutoConfigureAfter(FF4JConfiguration::class, SpringDocConfiguration::class)
@ConditionalOnClass(SpringDocConfiguration::class)
class FF4JOpenApiConfiguration {

  private val log: Logger = LoggerFactory.getLogger(FF4JOpenApiConfiguration::class.java)

  @Bean
  @ConditionalOnProperty(name = ["ff4j.api.spring-doc.enabled"], havingValue = "true", matchIfMissing = false)
  fun groupApiEnabled(environment: Environment): GroupedOpenApi {
    log.info("Initializing FF4J GroupedOpenApi configuration")
    // Bind from the Environment instead of injecting FF4JConfigurationProperties: this bean can be
    // created during the BeanFactoryPostProcessor phase, where the properties bean would exist but
    // never get bound (ConfigurationPropertiesBindingPostProcessor is not registered yet).
    val config = Binder.get(environment)
      .bindOrCreate("ff4j", FF4JConfigurationProperties::class.java)
    return GroupedOpenApi.builder().group(config.api.springDoc.group).pathsToMatch("${config.api.contextPath}/**")
      .build()
  }

  @Bean
  @ConditionalOnProperty(name = ["ff4j.api.spring-doc.enabled"], havingValue = "false", matchIfMissing = true)
  fun ff4jOpenApiPathsExclusion(
    config: FF4JConfigurationProperties,
    springDocConfigProperties: ObjectProvider<SpringDocConfigProperties>
  ): InitializingBean = InitializingBean {
    springDocConfigProperties.ifAvailable {
      log.info("Exclude FF4J OpenAPI configuration")
      it.pathsToExclude = listOf("${config.api.contextPath}/**")
    }
  }
}
