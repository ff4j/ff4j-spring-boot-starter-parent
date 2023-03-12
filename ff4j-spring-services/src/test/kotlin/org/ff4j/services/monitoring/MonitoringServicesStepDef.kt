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
package org.ff4j.services.monitoring

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.services.FF4JTestHelperUtils
import org.ff4j.services.InitializerStepDef
import org.ff4j.services.MonitoringServices
import org.ff4j.services.representation.PropertyPojo
import org.reactivestreams.Publisher
import reactor.test.StepVerifier

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class MonitoringServicesStepDef(ff4j: FF4j, monitoringServices: MonitoringServices) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var actualResponse: Any

  init {
    InitializerStepDef().initDataTable()

    Given("the property store is cleared") {
      testUtils.clearPropertyStore()
    }
    Given("the following properties exists in the property store") { dataTable: DataTable ->
      dataTable.asList(PropertyPojo::class.java).map {
        testUtils.convertToPropertyModel(it)
      }.forEach { ff4j.createProperty(it) }
    }
    Given("the feature store is cleared") {
      testUtils.clearFeatureStore()
    }
    Given("the following features exists in the feature store") { dataTable: DataTable ->
      val features = dataTable.asList(Feature::class.java)
      testUtils.createFeatures(features)
    }
    When("the user requests for the feature monitoring information") {
      actualResponse = monitoringServices.getMonitoringStatus()
    }
    Then("the user gets the response as") { expectedResponse: String ->
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { response: Any ->
        testUtils.assertLenientResponse(expectedResponse, response)
      }.verifyComplete()
    }
  }
}
