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

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ff4j")
data class FF4JConfigurationProperties(val api: Api = Api(),
                                       val audit: Audit = Audit(),
                                       val security: Security = Security(),
                                       val webConsole: WebConsole = WebConsole()
) {

  data class Api(val contextPath: String = "/api/ff4j", val springDoc: SpringDoc = SpringDoc())

  data class Audit(val enabled: Boolean = false)

  data class Security(val enabled: Boolean = false)

  data class WebConsole(val enabled: Boolean = false,
                        val contextPath: String = "/ff4j-web-console",
                        val security: WebConsoleSecurity = WebConsoleSecurity()
  )

  data class WebConsoleSecurity(val enabled: Boolean = false, val username: String = "admin", val password: String = "admin")

  data class SpringDoc(val enabled: Boolean = false, val group: String = "ff4j")
}
