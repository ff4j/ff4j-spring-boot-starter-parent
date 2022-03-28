/*-
 * #%L
 * ff4j-spring-boot-autoconfigure
 * %%
 * Copyright (C) 2013 - 2022 FF4J
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

import org.ff4j.FF4j
import org.ff4j.web.FF4jDispatcherServlet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@ConditionalOnClass(FF4jDispatcherServlet::class)
@AutoConfigureAfter(FF4JConfiguration::class)
@ConditionalOnProperty(
  value = ["ff4j.web-console.enabled"],
  havingValue = "true",
  matchIfMissing = false
)
class FF4JWebConsoleConfiguration : SpringBootServletInitializer(), WebMvcConfigurer {

  private val LOGGER: Logger = LoggerFactory.getLogger(FF4JWebConsoleConfiguration::class.java)

  @Value("\${ff4j.web-console.context-path:/ff4j-web-console}")
  private val contextPath: String? = null

  @Bean
  @ConditionalOnMissingBean
  fun getFF4jDispatcherServlet(ff4j: FF4j): FF4jDispatcherServlet? {
    LOGGER.info("Initializing the web console servlet");
    val ff4jConsoleServlet = FF4jDispatcherServlet()
    ff4jConsoleServlet.ff4j = ff4j
    return ff4jConsoleServlet
  }

  @Bean
  fun ff4jDispatcherServletRegistrationBean(ff4jDispatcherServlet: FF4jDispatcherServlet): ServletRegistrationBean<*> {
    LOGGER.info("Exposing FF4j web console on path '{}' ", contextPath)
    return ServletRegistrationBean(ff4jDispatcherServlet, "$contextPath/*")
  }
}
