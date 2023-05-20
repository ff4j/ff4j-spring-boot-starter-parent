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
package org.ff4j.services.group

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.services.FF4JTestHelperUtils
import org.ff4j.services.FeatureServices
import org.ff4j.services.GroupServices
import org.ff4j.services.InitializerStepDef

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class GroupServicesStepDef(
  ff4j: FF4j,
  groupServices: GroupServices,
  featureServices: FeatureServices
) : En {

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
    When("the user requests for group {string}") { groupName: String ->
      try {
        actualResponse = groupServices.getFeaturesByGroup(groupName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to enable group {string}") { groupName: String ->
      try {
        groupServices.enableGroup(groupName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests for a feature by feature id as {string}") { featureUID: String ->
      try {
        actualResponse = featureServices.getFeature(featureUID)
      } catch (throwable: Throwable) {
        exception = throwable
      }
    }
    When("the user requests to disable group {string}") { groupName: String ->
      try {
        groupServices.disableGroup(groupName)
      } catch (t: Throwable) {
        exception = t
      }
    }
    Then("the user gets the response as") { expectedResponse: String ->
      testUtils.assertLenientResponse(expectedResponse, actualResponse)
    }
    Then("the user gets an exception {string}") { className: String ->
      testUtils.assertException(exception, className)
    }
  }
}
