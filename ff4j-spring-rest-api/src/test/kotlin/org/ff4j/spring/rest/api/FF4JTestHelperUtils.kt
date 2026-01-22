/*-
 * #%L
 * ff4j-spring-rest-api
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
package org.ff4j.spring.rest.api

import org.apache.commons.lang3.StringUtils
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.property.Property
import org.ff4j.property.store.InMemoryPropertyStore
import org.ff4j.property.util.PropertyFactory
import org.ff4j.spring.rest.api.representation.PropertyPojo
import org.ff4j.spring.rest.api.representation.PropertyValueEnum
import org.ff4j.store.InMemoryFeatureStore

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class FF4JTestHelperUtils(val ff4j: FF4j) {

  fun createFeatures(features: List<Feature>) {
    features.forEach { ff4j.createFeature(it) }
  }

  fun clearFeatureStore() {
    ff4j.propertiesStore = InMemoryPropertyStore()
    ff4j.featureStore = InMemoryFeatureStore()
  }

  fun clearPropertyStore() {
    ff4j.propertiesStore = InMemoryPropertyStore()
    ff4j.featureStore = InMemoryFeatureStore()
  }

  fun convertToPropertyModel(propertyPojo: PropertyPojo): Property<*> =
    asProperty(
      propertyPojo.name,
      propertyPojo.type,
      propertyPojo.value,
      propertyPojo.description,
      if (StringUtils.isNotBlank(propertyPojo.fixedValueCSV) && propertyPojo.fixedValueCSV != "null") HashSet(
        propertyPojo.fixedValueCSV.split(",")
      ) else null
    )

  private fun asProperty(
    name: String?, type: String, value: String?, description: String?,
    fixedValues: Set<String?>?
  ): Property<*> {
    return PropertyFactory.createProperty(name, getClassname(type), value, description, fixedValues)
  }

  private fun getClassname(name: String): String {
    return when (val propertyEnum: PropertyValueEnum = PropertyValueEnum.getEnum(name)) {
      PropertyValueEnum.INT, PropertyValueEnum.LONG, PropertyValueEnum.STRING, PropertyValueEnum.BOOLEAN -> propertyEnum.getClassName()
        .orEmpty()
    }
  }
}
