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

import jakarta.annotation.PostConstruct
import org.ff4j.FF4j
import org.ff4j.security.SpringSecurityAuthorisationManager
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfigureAfter(FF4JConfiguration::class)
@ConditionalOnClass(FF4j::class)
class FF4JInitializationConfiguration(private val config: FF4JConfigurationProperties, val ff4j: FF4j) {

  @PostConstruct
  fun init() {
   ff4j.audit(config.audit.enabled)
    if (config.security.enabled) {
      ff4j.authorizationsManager = SpringSecurityAuthorisationManager()
    }
  }
}
