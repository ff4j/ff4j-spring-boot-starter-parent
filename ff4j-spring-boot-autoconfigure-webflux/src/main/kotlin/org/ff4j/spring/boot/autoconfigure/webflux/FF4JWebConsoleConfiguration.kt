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
package org.ff4j.spring.boot.autoconfigure.webflux

import org.ff4j.FF4j
import org.ff4j.spring.boot.autoconfigure.common.FF4JConfiguration
import org.ff4j.spring.boot.autoconfigure.common.FF4JConfigurationProperties
import org.ff4j.web.FF4jDispatcherServlet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean


@AutoConfiguration
@ConditionalOnClass(FF4jDispatcherServlet::class)
@AutoConfigureAfter(FF4JConfiguration::class)
@ConditionalOnProperty(
  value = ["ff4j.web-console.enabled"], havingValue = "true", matchIfMissing = true
)
class FF4JWebConsoleConfiguration(private val config: FF4JConfigurationProperties) : SpringBootServletInitializer() {

  private val log: Logger = LoggerFactory.getLogger(FF4JWebConsoleConfiguration::class.java)

  @Bean
  @ConditionalOnMissingBean
  fun getFF4jDispatcherServlet(ff4j: FF4j): FF4jDispatcherServlet? {
    log.info("Initializing the web console servlet");
    val ff4jConsoleServlet = FF4jDispatcherServlet()
    ff4jConsoleServlet.ff4j = ff4j
    return ff4jConsoleServlet
  }

  @Bean
  fun ff4jDispatcherServletRegistrationBean(ff4jDispatcherServlet: FF4jDispatcherServlet): ServletRegistrationBean<*> {
    log.info("Exposing FF4j web console on path '{}' ", config.webConsole.contextPath)
    return ServletRegistrationBean(ff4jDispatcherServlet, "${config.webConsole.contextPath}/*")
  }
}
