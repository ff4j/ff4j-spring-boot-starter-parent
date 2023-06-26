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

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@ConfigurationProperties(prefix = "ff4j")
data class FF4JConfigurationProperties(
  var api: Api = Api(),
  var audit: Audit = Audit(),
  var webConsole: WebConsole = WebConsole()
) {

  data class Api(var contextPath: String = "/api/ff4j", var springDoc: SpringDoc = SpringDoc())

  data class Audit(var enabled: Boolean = false)

  data class WebConsole(
    var enabled: Boolean = false,
    var contextPath: String = "/ff4j-web-console",
  )

  data class SpringDoc(var enabled: Boolean = false, var group: String = "ff4j")
}
