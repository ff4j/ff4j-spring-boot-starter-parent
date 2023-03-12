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

import org.apache.commons.lang3.StringUtils
import org.ff4j.FF4j
import org.ff4j.services.domain.CacheApiBean
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.domain.FeatureStoreApiBean
import org.ff4j.services.domain.GroupDescApiBean
import org.ff4j.services.exceptions.FeatureStoreNotCached
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class FeatureStoreServices(@Autowired val fF4j: FF4j) {
  fun getFeatureStore(): Mono<FeatureStoreApiBean> {
    return Mono.just(FeatureStoreApiBean(this.fF4j.featureStore))
  }

  fun getAllFeatures(): Flux<FeatureApiBean> {
    val allFeatures = fF4j.featureStore.readAll()
    return if (CollectionUtils.isEmpty(allFeatures)) {
      Flux.empty()
    } else {
      val features = ArrayList<FeatureApiBean>(allFeatures.size)
      features.addAll(allFeatures.values.stream().map { FeatureApiBean(it) }.collect(Collectors.toList()))
      Flux.fromIterable(features)
    }
  }

  fun getAllGroups(): Flux<GroupDescApiBean> {
    val groups = HashMap<String, GroupDescApiBean>()
    val allFeatures = fF4j.featureStore.readAll()
    allFeatures?.let {
      allFeatures.values.forEach {
        initGroup(groups, it.uid, it.group)
      }
    }
    return Flux.fromIterable(groups.values)
  }

  private fun initGroup(groups: HashMap<String, GroupDescApiBean>, uid: String, groupName: String?) {
    groupName?.let {
      if (StringUtils.isNotBlank(groupName)) {
        if (!groups.containsKey(groupName)) {
          groups[groupName] = GroupDescApiBean(groupName, ArrayList())
        }
        groups[groupName]?.features?.add(uid)
      }
    }
  }

  fun deleteAllFeatures() {
    fF4j.featureStore.clear()
  }

  fun getFeaturesFromCache(): Mono<CacheApiBean> {
    fF4j.cacheProxy ?: throw FeatureStoreNotCached()
    return Mono.just(CacheApiBean(fF4j.featureStore))
  }

  fun clearCachedFeatureStore() { // Fixing #218 : If audit is enabled, cannot clear cache.
    val cacheProxy = fF4j.cacheProxy ?: throw FeatureStoreNotCached()
    cacheProxy.cacheManager.clearFeatures()
  }
}
