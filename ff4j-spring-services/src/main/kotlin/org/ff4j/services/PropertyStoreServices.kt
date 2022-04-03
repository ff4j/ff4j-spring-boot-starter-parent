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
import org.ff4j.services.domain.CacheApiBean
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.services.domain.PropertyStoreApiBean
import org.ff4j.services.exceptions.PropertyStoreNotCached
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import java.util.stream.Collectors

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class PropertyStoreServices(@Autowired val ff4j: FF4j) {
  fun getPropertyStore(): PropertyStoreApiBean {
    return PropertyStoreApiBean(ff4j.propertiesStore)
  }

  fun getAllProperties(): List<PropertyApiBean> {
    val allProperties = ff4j.propertiesStore.readAllProperties()
    return if (CollectionUtils.isEmpty(allProperties)) {
      ArrayList(0)
    } else {
      allProperties.values.stream().map { PropertyApiBean(it) }.collect(Collectors.toList())
    }
  }

  fun deleteAllProperties() {
    ff4j.propertiesStore.clear()
  }

  fun getPropertiesFromCache(): CacheApiBean {
    ff4j.cacheProxy ?: throw PropertyStoreNotCached()
    return CacheApiBean(ff4j.propertiesStore)
  }

  fun clearCachedPropertyStore() {
    val cacheProxy = ff4j.cacheProxy ?: throw PropertyStoreNotCached()
    cacheProxy.cacheManager.clearProperties()
  }
}
