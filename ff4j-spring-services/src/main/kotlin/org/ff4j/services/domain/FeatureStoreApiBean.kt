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
package org.ff4j.services.domain

import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.core.FeatureStore
import java.io.Serializable

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class FeatureStoreApiBean : Serializable {

  companion object {
    private const val serialVersionUID = 1868920596870427435L
  }

  var type: String? = null
  var numberOfFeatures: Int = 0
  var numberOfGroups: Int = 0
  var features: MutableList<String> = ArrayList()
  var groups: MutableList<String> = ArrayList()
  var cache: CacheApiBean? = null

  constructor() : super()

  constructor(featureStore: FeatureStore) {
    type = featureStore.javaClass.canonicalName
    if (isInstanceOfCache(featureStore)) {
      cache = CacheApiBean(featureStore)
    }
    features = ArrayList(featureStore.readAll().keys)
    groups = ArrayList(featureStore.readAllGroups())
    numberOfFeatures = features.size
    numberOfGroups = groups.size
  }

  private fun isInstanceOfCache(featureStore: FeatureStore) = featureStore is FF4jCacheProxy
}
