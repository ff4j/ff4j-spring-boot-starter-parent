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
package org.ff4j.services.feature

import com.google.gson.Gson
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.services.FF4JTestHelperUtils
import org.ff4j.services.FeatureServices
import org.ff4j.services.InitializerStepDef
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.model.FeatureActions
import org.reactivestreams.Publisher
import reactor.test.StepVerifier
import java.lang.Boolean
import kotlin.String
import kotlin.Throwable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FeatureServicesStepDef(ff4j: FF4j, featureServices: FeatureServices) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var actualResponse: Any
  private lateinit var exception: Throwable

  init {
    InitializerStepDef().initDataTable()

    Given("the feature store is cleared") {
      testUtils.clearFeatureStore()
    }
    Given("the feature with {string}, {string}, {string}, {string} and {string} exists in the feature store") { uid: String, enabled: String, description: String, group: String, permissions: String ->
      val feature =
        Feature(uid, Boolean.valueOf(enabled), description, group, permissions.split(","))
      testUtils.createFeatures(listOf(feature))
    }
    Given("the following features exists in the feature store") { dataTable: DataTable ->
      val features = dataTable.asList(Feature::class.java)
      testUtils.createFeatures(features)
    }
    When("the user requests for a feature by feature id as {string}") { featureUID: String ->
      try {
        actualResponse = featureServices.getFeature(featureUID)
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
    When("the user requests to delete a feature with feature id as {string}") { featureUID: String ->
      try {
        featureServices.deleteFeature(featureUID)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to disable a feature with feature id as {string}") { featureUID: String ->
      try {
        featureServices.disableFeature(featureUID)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to enable a feature with feature id as {string}") { featureUID: String ->
      try {
        featureServices.enableFeature(featureUID)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to grant role {string} to a feature with feature id as {string}") { roleName: String, featureUID: String ->
      try {
        featureServices.grantRoleToFeature(featureUID, roleName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to remove role {string} to a feature with feature id as {string}") { roleName: String, featureUID: String ->
      try {
        featureServices.removeRoleFromFeature(featureUID, roleName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to add group {string} to a feature with feature id as {string}") { groupName: String, featureUID: String ->
      try {
        featureServices.addGroupToFeature(featureUID, groupName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to remove group {string} to a feature with feature id as {string}") { groupName: String, featureUID: String ->
      try {
        featureServices.removeGroupFromFeature(featureUID, groupName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    Then("the user gets an exception {string}") { className: String ->
      testUtils.assertException(exception, className)
    }
    Then("the user gets the response as {string}, {string}, {string}, {string} and {string}") { expectedUid: String, expectedEnabled: String, expectedDescription: String, expectedGroup: String, expectedPermissions: String ->
      val expectedFeature = Feature(
        expectedUid,
        Boolean.valueOf(expectedEnabled),
        expectedDescription,
        expectedGroup,
        expectedPermissions.split(",")
      )
      val expectedFeatureApiBean = FeatureApiBean(expectedFeature)
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualBean: Any ->
        assertThat(actualBean).isEqualToComparingFieldByField(expectedFeatureApiBean)
      }.verifyComplete()
    }
    Then("feature is created") {
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualBean: Any ->
        assertThat(actualBean).isEqualTo(FeatureActions.CREATED)
      }.verifyComplete()
    }
    Then("feature is updated") {
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualBean: Any ->
        assertThat(actualBean).isEqualTo(FeatureActions.UPDATED)
      }.verifyComplete()
    }
    Then("the user gets the response as") { expectedResponse: String ->
      val featureApiBean: FeatureApiBean =
        Gson().fromJson(expectedResponse, FeatureApiBean::class.java)
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { actualBean: Any ->
        assertThat(actualBean).isEqualToComparingOnlyGivenFields(featureApiBean)
      }.verifyComplete()
    }
  }
}
