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
import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.cache.InMemoryCacheManager
import org.ff4j.core.Feature
import org.ff4j.services.exceptions.FeatureStoreNotCached
import org.ff4j.store.InMemoryFeatureStore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Outcome-level assertions on [FeatureStoreServices]. The Cucumber suite drives the same methods
 * but largely stops at "a response was produced"; these assert on the emitted payloads, on the
 * grouping rules, and on the side effects (store cleared, cache cleared) actually happening.
 */
class FeatureStoreServicesUnitTest {

  private lateinit var ff4j: FF4j
  private lateinit var featureStoreServices: FeatureStoreServices

  @BeforeEach
  fun setUp() {
    ff4j = FF4j()
    ff4j.featureStore = InMemoryFeatureStore()
    featureStoreServices = FeatureStoreServices(ff4j)
  }

  private fun createFeature(uid: String, group: String? = null) {
    ff4j.createFeature(
      if (group == null) Feature(uid, true, "$uid description")
      else Feature(uid, true, "$uid description", group)
    )
  }

  @Test
  fun theFeatureStoreBeanDescribesTheUnderlyingStore() {
    createFeature("first", "alpha")
    createFeature("second")

    val featureStore = featureStoreServices.getFeatureStore().block()!!

    assertThat(featureStore.type).isEqualTo(InMemoryFeatureStore::class.java.canonicalName)
    assertThat(featureStore.numberOfFeatures).isEqualTo(2)
    assertThat(featureStore.features).containsExactlyInAnyOrder("first", "second")
    assertThat(featureStore.groups).containsExactly("alpha")
    assertThat(featureStore.numberOfGroups).isEqualTo(1)
    assertThat(featureStore.cache).isNull()
  }

  @Test
  fun getAllFeaturesEmitsNothingForAnEmptyStore() {
    val features = featureStoreServices.getAllFeatures().collectList().block()!!

    assertThat(features).isEmpty()
  }

  @Test
  fun getAllFeaturesEmitsOneBeanPerStoredFeature() {
    createFeature("first", "alpha")
    createFeature("second", "beta")

    val features = featureStoreServices.getAllFeatures().collectList().block()!!

    assertThat(features).hasSize(2)
    assertThat(features.map { it.uid }).containsExactlyInAnyOrder("first", "second")
    assertThat(features.map { it.group }).containsExactlyInAnyOrder("alpha", "beta")
    assertThat(features.map { it.description }).containsExactlyInAnyOrder("first description", "second description")
    assertThat(features.map { it.enable }).containsOnly(true)
  }

  @Test
  fun getAllGroupsEmitsNothingForAnEmptyStore() {
    val groups = featureStoreServices.getAllGroups().collectList().block()!!

    assertThat(groups).isEmpty()
  }

  @Test
  fun getAllGroupsCollectsEveryFeatureOfAGroupUnderASingleEntry() {
    createFeature("first", "alpha")
    createFeature("second", "alpha")
    createFeature("third", "beta")

    val groups = featureStoreServices.getAllGroups().collectList().block()!!

    assertThat(groups.map { it.groupName }).containsExactlyInAnyOrder("alpha", "beta")
    assertThat(groups.first { it.groupName == "alpha" }.features).containsExactlyInAnyOrder("first", "second")
    assertThat(groups.first { it.groupName == "beta" }.features).containsExactly("third")
  }

  @Test
  fun getAllGroupsIgnoresFeaturesWithNoGroupOrABlankGroup() {
    createFeature("ungrouped")
    createFeature("blankGroup", "   ")
    createFeature("grouped", "alpha")

    val groups = featureStoreServices.getAllGroups().collectList().block()!!

    assertThat(groups).hasSize(1)
    assertThat(groups.single().groupName).isEqualTo("alpha")
    assertThat(groups.single().features).containsExactly("grouped")
  }

  @Test
  fun deleteAllFeaturesEmptiesTheStore() {
    createFeature("first", "alpha")
    createFeature("second")
    assertThat(ff4j.featureStore.readAll()).hasSize(2)

    featureStoreServices.deleteAllFeatures()

    assertThat(ff4j.featureStore.readAll()).isEmpty()
    assertThat(featureStoreServices.getAllFeatures().collectList().block()!!).isEmpty()
  }

  @Test
  fun getFeaturesFromCacheFailsWhenTheStoreIsNotCached() {
    assertThatThrownBy { featureStoreServices.getFeaturesFromCache() }
      .isInstanceOf(FeatureStoreNotCached::class.java)
  }

  @Test
  fun getFeaturesFromCacheDescribesTheCachedFeatures() {
    val cacheManager = InMemoryCacheManager()
    ff4j.featureStore = FF4jCacheProxy(InMemoryFeatureStore(), null, cacheManager)
    cacheManager.putFeature(Feature("cached", true, "cached description"))

    val cache = featureStoreServices.getFeaturesFromCache().block()!!

    assertThat(cache.featureNames).containsExactly("cached")
    assertThat(cache.cacheProvider).isEqualTo(cacheManager.cacheProviderName)
    assertThat(cache.cacheStore).isEqualTo(InMemoryFeatureStore::class.java.canonicalName)
  }

  @Test
  fun clearCachedFeatureStoreFailsWhenTheStoreIsNotCached() {
    assertThatThrownBy { featureStoreServices.clearCachedFeatureStore() }
      .isInstanceOf(FeatureStoreNotCached::class.java)
  }

  @Test
  fun clearCachedFeatureStoreEvictsTheCacheButKeepsTheFeatures() {
    val targetStore = InMemoryFeatureStore()
    val cacheManager = InMemoryCacheManager()
    ff4j.featureStore = FF4jCacheProxy(targetStore, null, cacheManager)
    ff4j.createFeature(Feature("cached", true, "cached description"))
    cacheManager.putFeature(Feature("cached", true, "cached description"))
    assertThat(cacheManager.listCachedFeatureNames()).containsExactly("cached")

    featureStoreServices.clearCachedFeatureStore()

    assertThat(cacheManager.listCachedFeatureNames()).isEmpty()
    assertThat(targetStore.readAll().keys).containsExactly("cached")
  }
}
