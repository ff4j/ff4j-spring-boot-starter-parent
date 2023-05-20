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

import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.core.FeatureStore
import org.ff4j.property.store.PropertyStore
import java.io.Serializable

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class CacheApiBean : Serializable {

  companion object {
    private const val serialVersionUID = -2564221971597313125L
  }

  var cacheProvider: String? = null
  var cacheStore: String? = null
  var featureNames: MutableSet<String> = HashSet()
  var propertyNames: MutableSet<String> = HashSet()

  constructor() : super()

  constructor(featureStore: FeatureStore) {
    if (featureStore is FF4jCacheProxy) {
      this.cacheStore = featureStore.cachedTargetStore
      this.cacheProvider = featureStore.cacheProvider
      this.featureNames = featureStore.cacheManager.listCachedFeatureNames()
    }
  }

  constructor(propertyStore: PropertyStore) {
    if (propertyStore is FF4jCacheProxy) {
      this.cacheStore = propertyStore.cachedTargetStore
      this.cacheProvider = propertyStore.cacheProvider
      this.propertyNames = propertyStore.cacheManager.listCachedPropertyNames()
    }
  }
}
