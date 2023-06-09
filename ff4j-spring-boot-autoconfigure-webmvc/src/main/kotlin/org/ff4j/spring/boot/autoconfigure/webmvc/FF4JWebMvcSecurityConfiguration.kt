/*-
 * #%L
 * ff4j-spring-boot-autoconfigure-webmvc
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
package org.ff4j.spring.boot.autoconfigure.webmvc

import org.ff4j.FF4j
import org.ff4j.security.SpringSecurityAuthorisationManager
import org.ff4j.spring.boot.autoconfigure.common.FF4JConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@AutoConfiguration
@AutoConfigureAfter(FF4JConfiguration::class)
@ConditionalOnProperty(
  value = ["ff4j.security.enabled"], havingValue = "true", matchIfMissing = false
)
class FF4JWebMvcSecurityConfiguration(ff4j: FF4j) {
  init {
    ff4j.authorizationsManager = SpringSecurityAuthorisationManager()
  }
}
