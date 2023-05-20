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
package org.ff4j.services.domain

import org.ff4j.core.Feature
import org.ff4j.exception.FeatureAccessException
import org.ff4j.services.exceptions.FlippingStrategyBadRequestException
import org.ff4j.services.exceptions.PropertiesBadRequestException
import org.ff4j.utils.MappingUtil
import java.io.Serializable

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class FeatureApiBean : Serializable {

  companion object {
    private const val serialVersionUID = -4977143873952901044L
  }

  var uid: String? = null
  var description: String? = null
  var group: String? = null
  var flippingStrategy: FlippingStrategyApiBean? = null
  var permissions: MutableSet<String> = HashSet()
  var customProperties: MutableMap<String, PropertyApiBean> = HashMap()
  var enable: Boolean = false

  constructor() : super()

  constructor(feature: Feature) {
    this.uid = feature.uid
    this.enable = feature.isEnable
    this.description = feature.description
    this.permissions = feature.permissions
    this.group = feature.group
    feature.flippingStrategy?.let {
      this.flippingStrategy = FlippingStrategyApiBean(it)
    }
    feature.customProperties?.let {
      it.values.forEach { property ->
        run {
          this.customProperties[property.name] = PropertyApiBean(property)
        }
      }
    }
  }

  fun toFeature(): Feature {
    val feature = Feature(uid)
    feature.description = description
    feature.isEnable = enable
    feature.group = group
    feature.permissions = permissions
    initFlippingStrategy(feature)
    initProperties(feature)
    return feature
  }

  private fun initFlippingStrategy(feature: Feature) {
    flippingStrategy?.let {
      try {
        feature.flippingStrategy = MappingUtil.instanceFlippingStrategy(uid, it.type, it.initParams)
      } catch (exception: FeatureAccessException) {
        throw FlippingStrategyBadRequestException(exception)
      }
    }
  }

  private fun initProperties(feature: Feature) {
    addProperty(customProperties.values, feature)
  }

  private fun addProperty(values: MutableCollection<PropertyApiBean>, feature: Feature) {
    values.forEach { propertyApiBean ->
      run {
        try {
          feature.addProperty(propertyApiBean.asProperty())
        } catch (exception: IllegalArgumentException) {
          throw PropertiesBadRequestException(exception)
        }
      }
    }
  }
}
