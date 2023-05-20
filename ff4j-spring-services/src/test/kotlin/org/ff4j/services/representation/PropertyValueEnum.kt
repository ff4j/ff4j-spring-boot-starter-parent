
/*-
 * #%L
 * ff4j-spring-services
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
package org.ff4j.services.representation

import org.apache.commons.lang3.StringUtils
import org.ff4j.property.PropertyBoolean
import org.ff4j.property.PropertyInt
import org.ff4j.property.PropertyLong
import org.ff4j.property.PropertyString

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
enum class PropertyValueEnum(private val type: String, private val className: String?) {

  INT("int", PropertyInt::class.qualifiedName),
  LONG("long", PropertyLong::class.qualifiedName),
  STRING("string", PropertyString::class.qualifiedName),
  BOOLEAN("boolean", PropertyBoolean::class.qualifiedName),
  ;

  open fun getClassName(): String? = className

  open fun getType(): String = type

  companion object {

    fun getEnum(name: String): PropertyValueEnum {
      for (propertyValueEnum in values()) {
        if (StringUtils.isNotBlank(name) && name.equals(
            propertyValueEnum.type,
            ignoreCase = true
          )
        ) {
          return propertyValueEnum
        }
      }
      throw UnsupportedOperationException("property $name not found")
    }
  }
}
