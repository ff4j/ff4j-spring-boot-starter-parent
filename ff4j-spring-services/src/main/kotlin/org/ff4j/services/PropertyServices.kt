/*-
 * #%L
 * ff4j-spring-services
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
package org.ff4j.services

import org.ff4j.FF4j
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.services.model.FeatureActions
import org.ff4j.services.model.FeatureActions.CREATED
import org.ff4j.services.model.FeatureActions.UPDATED
import org.ff4j.services.validator.PropertyValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class PropertyServices(@Autowired val fF4j: FF4j, @Autowired val propertyValidator: PropertyValidator) {
  fun getProperty(propertyUID: String): Mono<PropertyApiBean> {
    propertyValidator.assertPropertyExist(propertyUID)
    return Mono.just(PropertyApiBean(fF4j.propertiesStore.readProperty(propertyUID)))
  }

  fun createOrUpdateProperty(propertyUID: String, propertyApiBean: PropertyApiBean): Mono<FeatureActions> {
    propertyValidator.assertPropertyNameNotBlank(propertyApiBean.name)
    propertyValidator.assertPropertyNameMatch(propertyUID, propertyApiBean.name)
    val property = propertyApiBean.asProperty()
    return if (fF4j.propertiesStore.existProperty(propertyUID)) {
      fF4j.propertiesStore.updateProperty(property)
      Mono.just(UPDATED)
    } else {
      fF4j.propertiesStore.createProperty(property)
      Mono.just(CREATED)
    }
  }

  fun deleteProperty(propertyUID: String) {
    propertyValidator.assertPropertyExist(propertyUID)
    fF4j.propertiesStore.deleteProperty(propertyUID)
  }

  fun updatePropertyName(propertyUID: String, newPropertyName: String) {
    propertyValidator.assertPropertyExist(propertyUID)
    fF4j.propertiesStore.updateProperty(propertyUID, newPropertyName)
  }
}
