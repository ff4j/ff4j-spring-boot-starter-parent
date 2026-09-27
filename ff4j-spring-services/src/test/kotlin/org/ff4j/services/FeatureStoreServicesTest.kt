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
package org.ff4j.services

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.ff4j.FF4j
import org.ff4j.cache.InMemoryCacheManager
import org.ff4j.core.Feature
import org.ff4j.services.exceptions.FeatureStoreNotCached
import org.ff4j.store.InMemoryFeatureStore
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

/**
 * Unit tests for [FeatureStoreServices] against a real in-memory FF4j instance.
 */
class FeatureStoreServicesTest {

  private fun feature(uid: String, group: String? = null, enable: Boolean = false): Feature {
    val feature = Feature(uid)
    feature.isEnable = enable
    feature.group = group
    return feature
  }

  private fun ff4jWith(vararg features: Feature): FF4j {
    val ff4j = FF4j()
    features.forEach { ff4j.createFeature(it) }
    return ff4j
  }

  @Test
  fun getFeatureStoreDescribesTheUnderlyingStore() {
    val ff4j = ff4jWith(feature("f1", "g1"), feature("f2"))

    val store = FeatureStoreServices(ff4j).getFeatureStore().block()!!

    assertThat(store.type).isEqualTo(InMemoryFeatureStore::class.java.canonicalName)
    assertThat(store.numberOfFeatures).isEqualTo(2)
    assertThat(store.features).containsExactlyInAnyOrder("f1", "f2")
    assertThat(store.groups).containsExactly("g1")
    assertThat(store.cache).isNull()
  }

  @Test
  fun getAllFeaturesIsEmptyWhenStoreIsEmpty() {
    StepVerifier.create(FeatureStoreServices(FF4j()).getAllFeatures()).verifyComplete()
  }

  @Test
  fun getAllFeaturesMapsEveryFeature() {
    val ff4j = ff4jWith(feature("f1", "g1", enable = true), feature("f2"))

    val features = FeatureStoreServices(ff4j).getAllFeatures().collectList().block()!!

    assertThat(features).hasSize(2)
    assertThat(features.map { it.uid }).containsExactlyInAnyOrder("f1", "f2")
    val f1 = features.first { it.uid == "f1" }
    assertThat(f1.enable).isTrue
    assertThat(f1.group).isEqualTo("g1")
  }

  @Test
  fun getAllGroupsIsEmptyWhenStoreIsEmpty() {
    StepVerifier.create(FeatureStoreServices(FF4j()).getAllGroups()).verifyComplete()
  }

  @Test
  fun getAllGroupsCollectsFeaturesPerNonBlankGroup() {
    val ff4j = ff4jWith(
      feature("f1", "g1"),
      feature("f2", "g1"),
      feature("f3", "g2"),
      feature("f4"),
      feature("f5", "   ")
    )

    val groups = FeatureStoreServices(ff4j).getAllGroups().collectList().block()!!
      .associate { it.groupName to it.features }

    assertThat(groups).containsOnlyKeys("g1", "g2")
    assertThat(groups["g1"]).containsExactlyInAnyOrder("f1", "f2")
    assertThat(groups["g2"]).containsExactly("f3")
  }

  @Test
  fun deleteAllFeaturesEmptiesTheStore() {
    val ff4j = ff4jWith(feature("f1"), feature("f2"))

    FeatureStoreServices(ff4j).deleteAllFeatures()

    assertThat(ff4j.featureStore.readAll()).isEmpty()
  }

  @Test
  fun getFeaturesFromCacheFailsWhenNotCached() {
    assertThatThrownBy { FeatureStoreServices(FF4j()).getFeaturesFromCache() }
      .isInstanceOf(FeatureStoreNotCached::class.java)
  }

  @Test
  fun getFeaturesFromCacheDescribesTheCache() {
    val ff4j = ff4jWith(feature("f1"))
    val cacheManager = InMemoryCacheManager()
    ff4j.cache(cacheManager)
    ff4j.featureStore.read("f1")

    val cache = FeatureStoreServices(ff4j).getFeaturesFromCache().block()!!

    assertThat(cache.cacheProvider).isEqualTo(cacheManager.cacheProviderName)
    assertThat(cache.cacheStore).isEqualTo(ff4j.cacheProxy.cachedTargetStore)
    assertThat(cache.featureNames).containsExactly("f1")
  }

  @Test
  fun clearCachedFeatureStoreFailsWhenNotCached() {
    assertThatThrownBy { FeatureStoreServices(FF4j()).clearCachedFeatureStore() }
      .isInstanceOf(FeatureStoreNotCached::class.java)
  }

  @Test
  fun clearCachedFeatureStoreEvictsCachedFeatures() {
    val ff4j = ff4jWith(feature("f1"))
    val cacheManager = InMemoryCacheManager()
    ff4j.cache(cacheManager)
    ff4j.featureStore.read("f1")
    assertThat(cacheManager.listCachedFeatureNames()).containsExactly("f1")

    FeatureStoreServices(ff4j).clearCachedFeatureStore()

    assertThat(cacheManager.listCachedFeatureNames()).isEmpty()
    assertThat(ff4j.featureStore.exist("f1")).isTrue
  }
}
