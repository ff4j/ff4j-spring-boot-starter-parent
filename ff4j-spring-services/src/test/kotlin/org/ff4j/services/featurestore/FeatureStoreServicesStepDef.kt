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
package org.ff4j.services.featurestore

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.ff4j.FF4j
import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.cache.InMemoryCacheManager
import org.ff4j.core.Feature
import org.ff4j.services.FF4JTestHelperUtils
import org.ff4j.services.FeatureStoreServices
import org.ff4j.services.InitializerStepDef

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FeatureStoreServicesStepDef(ff4j: FF4j, featureStoreServices: FeatureStoreServices) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var actualResponse: Any
  private lateinit var exception: Throwable

  init {
    InitializerStepDef().initDataTable()

    Given("the feature store is cleared") {
      testUtils.clearFeatureStore()
    }
    Given("the following features exists in the feature store") { dataTable: DataTable ->
      val features = dataTable.asList(Feature::class.java)
      testUtils.createFeatures(features)
    }
    Given("the feature store is cached") {
      val proxy = FF4jCacheProxy(ff4j.featureStore, null, InMemoryCacheManager())
      ff4j.featureStore = proxy
    }
    Given("the following features are cached") { dataTable: DataTable ->
      val features = dataTable.asList(Feature::class.java)
      features.forEach {
        (ff4j.featureStore as FF4jCacheProxy).cacheManager.putFeature(it)
      }
    }
    When("the user requests for the feature store") {
      actualResponse = featureStoreServices.getFeatureStore()
    }
    When("the user requests for all the features from the feature store") {
      actualResponse = featureStoreServices.getAllFeatures()
    }
    When("the user requests for all the groups from the feature store") {
      actualResponse = featureStoreServices.getAllGroups()
    }
    When("the user requests to delete all the features from the feature store") {
      featureStoreServices.deleteAllFeatures()
    }
    When("the user requests to get the cached feature store") {
      try {
        actualResponse = featureStoreServices.getFeaturesFromCache()
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to clear the cached feature store") {
      try {
        featureStoreServices.clearCachedFeatureStore()
      } catch (t: Throwable) {
        exception = t
      }
    }
    Then("the user gets the response as") { _: String ->
      /*StepVerifier.create(actualResponse).consumeNextWith { actualFeatureApiBean: FeatureApiBean ->
        testUtils.assertLenientResponse(expectedResponse, actualFeatureApiBean)
      }.verifyComplete()*/
    }
    Then("the user gets an exception {string}") { className: String ->
      testUtils.assertException(exception, className)
    }
  }
}
