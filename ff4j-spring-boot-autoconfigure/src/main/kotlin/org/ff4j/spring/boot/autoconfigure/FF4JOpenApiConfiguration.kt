/*-
 * #%L
 * ff4j-spring-boot-web-api
 * %%
 * Copyright (C) 2013 - 2019 FF4J
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

package org.ff4j.spring.boot.autoconfigure

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@OpenAPIDefinition(
  info = Info(
    title = "FF4J (ff4j.org) WebAPI",
    version = "1.8",
    description = "Administer and operate all tasks on your features through this api.",
    license = License(
      name = "Apache 2.0",
      url = "http://www.apache.org/licenses/LICENSE-2.0.html"
    ),
    contact = Contact(
      name = "Paul Williams",
      email = "paul58914080@gmail.com",
      url = "paul-williams.me"
    ),
    termsOfService = "http://www.ff4j.org/terms-of-service"
  )
)
@Configuration
open class FF4JOpenApiConfiguration {

  @Value("\${ff4j.springdoc.enabled:false}")
  private val springDocEnabled: Boolean = false

  @Bean
  fun groupApiEnabled(): GroupedOpenApi {
    return when (springDocEnabled) {
      true -> GroupedOpenApi.builder().group("ff4j").pathsToMatch("/api/ff4j/**").build()
      else -> GroupedOpenApi.builder().group("ff4j").pathsToExclude("/api/ff4j/**").build()
    }
  }
}
