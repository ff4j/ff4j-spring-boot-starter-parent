/*-
 * #%L
 * ff4j-spring-boot-autoconfigure
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

package org.ff4j.spring.boot.webmvc.autoconfigure

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.ff4j.spring.boot.autoconfigure.common.FF4JConfigurationProperties
import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.SpringDocConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean


/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@OpenAPIDefinition(
  info = Info(
    title = "FF4J (ff4j.org) WebAPI",
    version = "2.0.0",
    description = "Administer and operate all tasks on your features through this api.",
    license = License(
      name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"
    ),
    contact = Contact(
      name = "Paul Williams", email = "paul58914080@gmail.com", url = "paul-williams.me"
    ),
    termsOfService = "http://www.ff4j.org/terms-of-service"
  )
)
@ConditionalOnClass(SpringDocConfiguration::class)
@ConditionalOnWebApplication
@AutoConfiguration
class FF4JOpenApiConfiguration(private val config: FF4JConfigurationProperties) {

  @Bean
  fun groupApiEnabled(): GroupedOpenApi {
    return when (config.api.springDoc.enabled) {
      true -> GroupedOpenApi.builder().group(config.api.springDoc.group).pathsToMatch("${config.api.contextPath}/**").build()
      else -> GroupedOpenApi.builder().group(config.api.springDoc.group).pathsToExclude("${config.api.contextPath}/**").build()
    }
  }
}
