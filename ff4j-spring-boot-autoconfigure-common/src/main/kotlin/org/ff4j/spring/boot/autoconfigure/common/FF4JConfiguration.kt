/*-
 * #%L
 * ff4j-spring-boot-autoconfigure-common
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
package org.ff4j.spring.boot.autoconfigure.common

import org.ff4j.FF4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@AutoConfiguration
@ConditionalOnClass(FF4j::class)
@ComponentScan(value = ["org.ff4j.spring.rest.api", "org.ff4j.services", "org.ff4j.aop", "org.ff4j.spring"])
@ConfigurationPropertiesScan
class FF4JConfiguration {
  private val log: Logger = LoggerFactory.getLogger(FF4JConfiguration::class.java)

  @Value("\${ff4j.audit.enabled:false}")
  var isAuditEnabled = false

  @Bean
  @ConditionalOnMissingBean
  fun getFF4J(): FF4j {
    log.info("Initializing FF4J")
    val ff4j = FF4j()
    log.info("FF4J isAuditEnabled: {}", isAuditEnabled)
    ff4j.audit(isAuditEnabled)
    return ff4j
  }
}
