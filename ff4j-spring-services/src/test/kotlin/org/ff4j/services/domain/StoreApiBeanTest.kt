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

import org.assertj.core.api.Assertions.assertThat
import org.ff4j.audit.chart.PieChart
import org.ff4j.audit.chart.Serie
import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.cache.InMemoryCacheManager
import org.ff4j.core.Feature
import org.ff4j.property.PropertyString
import org.ff4j.property.store.InMemoryPropertyStore
import org.ff4j.store.InMemoryFeatureStore
import org.junit.jupiter.api.Test

/**
 * Unit tests for the store/cache/feature/chart API beans' mapping constructors.
 */
class StoreApiBeanTest {

  private fun featureStoreWith(vararg uids: String): InMemoryFeatureStore {
    val store = InMemoryFeatureStore()
    uids.forEach { store.create(Feature(it)) }
    return store
  }

  @Test
  fun featureStoreApiBeanForPlainStoreHasNoCache() {
    val bean = FeatureStoreApiBean(featureStoreWith("f1", "f2"))

    assertThat(bean.cache).isNull()
    assertThat(bean.type).isEqualTo(InMemoryFeatureStore::class.java.canonicalName)
    assertThat(bean.numberOfFeatures).isEqualTo(2)
  }

  @Test
  fun featureStoreApiBeanForCachedStoreDescribesCache() {
    val cacheManager = InMemoryCacheManager()
    val proxy = FF4jCacheProxy(featureStoreWith("f1"), InMemoryPropertyStore(), cacheManager)
    proxy.read("f1")

    val bean = FeatureStoreApiBean(proxy)

    assertThat(bean.cache).isNotNull
    assertThat(bean.cache!!.cacheProvider).isEqualTo(cacheManager.cacheProviderName)
    assertThat(bean.cache!!.featureNames).containsExactly("f1")
  }

  @Test
  fun cacheApiBeanForPlainFeatureStoreIsEmpty() {
    val bean = CacheApiBean(featureStoreWith("f1"))

    assertThat(bean.cacheStore).isNull()
    assertThat(bean.cacheProvider).isNull()
    assertThat(bean.featureNames).isEmpty()
  }

  @Test
  fun cacheApiBeanForPlainPropertyStoreIsEmpty() {
    val bean = CacheApiBean(InMemoryPropertyStore())

    assertThat(bean.cacheStore).isNull()
    assertThat(bean.cacheProvider).isNull()
    assertThat(bean.propertyNames).isEmpty()
  }

  @Test
  fun propertyStoreApiBeanForPlainStoreHasNoCache() {
    val store = InMemoryPropertyStore()
    store.createProperty(PropertyString("p1", "v1"))

    val bean = PropertyStoreApiBean(store)

    assertThat(bean.cache).isNull()
    assertThat(bean.properties).containsExactly("p1")
    assertThat(bean.numberOfProperties).isEqualTo(1)
  }

  @Test
  fun propertyStoreApiBeanForCachedStoreDescribesCache() {
    val cacheManager = InMemoryCacheManager()
    val proxy = FF4jCacheProxy(InMemoryFeatureStore(), InMemoryPropertyStore(), cacheManager)

    val bean = PropertyStoreApiBean(proxy)

    assertThat(bean.cache).isNotNull
    assertThat(bean.cache!!.cacheProvider).isEqualTo(cacheManager.cacheProviderName)
  }

  @Test
  fun featureApiBeanCopiesEveryField() {
    val feature = Feature("f1")
    feature.isEnable = true
    feature.description = "the description"
    feature.group = "g1"
    feature.permissions = mutableSetOf("ADMIN", "USER")
    feature.addProperty(PropertyString("p1", "v1"))

    val bean = FeatureApiBean(feature)

    assertThat(bean.uid).isEqualTo("f1")
    assertThat(bean.enable).isTrue
    assertThat(bean.description).isEqualTo("the description")
    assertThat(bean.group).isEqualTo("g1")
    assertThat(bean.permissions).containsExactlyInAnyOrder("ADMIN", "USER")
    assertThat(bean.customProperties).containsOnlyKeys("p1")
    assertThat(bean.customProperties["p1"]!!.value).isEqualTo("v1")
  }

  @Test
  fun featureApiBeanCopiesDisabledState() {
    assertThat(FeatureApiBean(Feature("f1")).enable).isFalse
  }

  @Test
  fun pieChartApiBeanCopiesSectors() {
    val pieChart = PieChart("hits")
    pieChart.sectors.add(Serie("f1", 3, "FF0000"))
    pieChart.sectors.add(Serie("f2", 5, "00FF00"))

    val bean = PieChartApiBean(pieChart)

    assertThat(bean.title).isEqualTo("hits")
    assertThat(bean.sectors).hasSize(2)
    assertThat(bean.sectors[0].label).isEqualTo("f1")
    assertThat(bean.sectors[0].value).isEqualTo(3)
    assertThat(bean.sectors[0].color).isEqualTo("FF0000")
    assertThat(bean.sectors[1].value).isEqualTo(5)
  }

  @Test
  fun barSeriesApiBeanCopiesSerie() {
    val bean = BarSeriesApiBean(Serie("f1", 2.5, "0000FF"))

    assertThat(bean.label).isEqualTo("f1")
    assertThat(bean.value).isEqualTo(2.5)
    assertThat(bean.color).isEqualTo("0000FF")
  }
}
