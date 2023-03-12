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
package org.ff4j.services.ff4j

import com.google.gson.Gson
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.services.FF4JTestHelperUtils
import org.ff4j.services.FF4jServices
import org.ff4j.services.FeatureServices
import org.ff4j.services.InitializerStepDef
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.model.FeatureActions
import org.ff4j.services.representation.TestAuthorizationsManager
import org.reactivestreams.Publisher
import reactor.test.StepVerifier

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FF4JServicesStepDef(
  ff4j: FF4j, ff4jServices: FF4jServices, featureServices: FeatureServices
) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var actualResponse: Any
  private lateinit var exception: Throwable

  init {
    InitializerStepDef().initDataTable()

    Given("the feature store is cleared") {
      testUtils.clearFeatureStore()
    }
    Given("the authorization manager is cleared") {
      ff4j.authorizationsManager = null
    }
    Given("the following features exists in the feature store") { dataTable: DataTable ->
      val features = dataTable.asList(Feature::class.java)
      testUtils.createFeatures(features)
    }
    Given("the feature store has the following security information") { dataTable: DataTable ->
      val authorizationsManagers = dataTable.asList(TestAuthorizationsManager::class.java)
      if (authorizationsManagers.size > 1) throw AssertionError("there should and can be only one AuthorizationManager")
      else ff4j.authorizationsManager = authorizationsManagers[0]
    }
    When("the user requests for status") {
      actualResponse = ff4jServices.getStatus()
    }
    When("the user requests for security") {
      try {
        actualResponse = ff4jServices.getSecurityInfo()
      } catch (throwable: Throwable) {
        exception = throwable
      }
    }
    When("the user requests to check if the feature is flipped with feature uid as {string}") { featureUID: String ->
      try {
        actualResponse = ff4jServices.check(featureUID)
      } catch (throwable: Throwable) {
        exception = throwable
      }
    }
    When("the user requests to create or update a feature with feature id as {string} and feature spec as") { featureUID: String, featureSpec: String ->
      val featureApiBean = Gson().fromJson(featureSpec, FeatureApiBean::class.java)
      try {
        actualResponse = featureServices.createOrUpdateFeature(featureUID, featureApiBean)
      } catch (throwable: Throwable) {
        exception = throwable
      }
    }
    When("the user requests for a feature by feature id as {string}") { featureUID: String ->
      try {
        actualResponse = featureServices.getFeature(featureUID)
      } catch (throwable: Throwable) {
        exception = throwable
      }
    }
    Then("the user gets an exception {string}") { className: String ->
      testUtils.assertException(exception, className)
    }
    Then("the user gets the response as") { expectedResponse: String ->
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualResponse: Any ->
        testUtils.assertLenientResponse(expectedResponse, actualResponse)
      }.verifyComplete()
    }
    Then("the user gets a response {string}") { expectedResponse: String ->
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualResponse: Any ->
        assertThat(actualResponse).isEqualTo(expectedResponse.toBooleanStrict())
      }.verifyComplete()
    }
    Then("feature is updated") {
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualResponse: Any ->
        assertThat(actualResponse).isEqualTo(FeatureActions.UPDATED)
      }.verifyComplete()
    }
    When("the user requests to check if the feature is flipped with feature uid as {string} and parameters") { featureUID: String, dataTable: DataTable ->
      val map = HashMap(dataTable.asMap())
      map.keys.forEach { map.replace(it, map[it]?.replace("or", "|")) }
      try {
        actualResponse = ff4jServices.check(featureUID, map)
      } catch (throwable: Throwable) {
        exception = throwable
      }
    }
  }
}
