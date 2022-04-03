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
import org.ff4j.security.SpringSecurityAuthorisationManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Configuration
@ConditionalOnClass(FF4j::class)
@ComponentScan(value = ["org.ff4j.spring.boot.web.api", "org.ff4j.services", "org.ff4j.aop", "org.ff4j.spring"])
class FF4JConfiguration {

  @Value("\${ff4j.audit.enabled:false}")
  private val auditEnabled: Boolean = false

  @Value("\${ff4j.security.enabled:false}")
  private val securityEnabled: Boolean = false

  @Bean
  @ConditionalOnMissingBean
  fun getFF4J(): FF4j {
    val ff4j = FF4j()
    ff4j.audit(auditEnabled)
    if (securityEnabled) {
      ff4j.authorizationsManager = SpringSecurityAuthorisationManager()
    }
    return ff4j
  }
}
