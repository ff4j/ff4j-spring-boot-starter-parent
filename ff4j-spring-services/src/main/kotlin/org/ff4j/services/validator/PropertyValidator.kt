/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2026 FF4J
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
package org.ff4j.services.validator

import org.apache.commons.lang3.StringUtils
import org.ff4j.FF4j
import org.ff4j.services.exceptions.PropertyNameBlankException
import org.ff4j.services.exceptions.PropertyNameNotMatchException
import org.ff4j.services.exceptions.PropertyNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Component
class PropertyValidator {
  @Autowired
  lateinit var ff4j: FF4j

  fun assertPropertyExist(propertyName: String) {
    if (!ff4j.propertiesStore.existProperty(propertyName)) {
      throw PropertyNotFoundException()
    }
  }

  fun assertPropertyNameNotBlank(name: String?) {
    if (StringUtils.isBlank(name)) {
      throw PropertyNameBlankException()
    }
  }

  fun assertPropertyNameMatch(propertyName: String, propertyApiBeanName: String?) {
    if (!propertyName.equals(propertyApiBeanName, ignoreCase = true)) {
      throw PropertyNameNotMatchException()
    }
  }
}
