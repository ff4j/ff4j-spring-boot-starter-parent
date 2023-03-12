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
package org.ff4j.services.property

import com.google.gson.Gson
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.ff4j.FF4j
import org.ff4j.services.FF4JTestHelperUtils
import org.ff4j.services.InitializerStepDef
import org.ff4j.services.PropertyServices
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.services.representation.PropertyPojo
import org.reactivestreams.Publisher
import reactor.test.StepVerifier

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class PropertyServicesStepDef(ff4j: FF4j, propertyServices: PropertyServices) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var actualResponse: Any
  private lateinit var exception: Throwable

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
    When("the user requests for a property by property id as {string}") { propertyUID: String ->
      try {
        actualResponse = propertyServices.getProperty(propertyUID)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to create or update a property with property id as {string} and property spec as") { propertyUID: String, propertySpec: String ->
      val propertyApiBean: PropertyApiBean =
        Gson().fromJson(propertySpec, PropertyApiBean::class.java)
      try {
        actualResponse = propertyServices.createOrUpdateProperty(propertyUID, propertyApiBean)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to delete a property with property id as {string}") { propertyUID: String ->
      try {
        propertyServices.deleteProperty(propertyUID)
      } catch (t: Throwable) {
        exception = t
      }
    }
    When("the user requests to update a property with property id as {string} and property value as {string}") { propertyUID: String, propertyValue: String ->
      try {
        propertyServices.updatePropertyName(propertyUID, propertyValue)
      } catch (t: Throwable) {
        exception = t
      }
    }
    Then("the user gets the response as") { expectedResponse: String ->
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { response: Any ->
        testUtils.assertLenientResponse(expectedResponse, response)
      }.verifyComplete()
    }
    Then("the user gets an exception {string}") { className: String ->
      testUtils.assertException(exception, className)
    }
    Then("property is created") {
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { response: Any ->
        testUtils.assertCreated(response)
      }.verifyComplete()
    }
    Then("property is updated") {
      StepVerifier.create(actualResponse as Publisher<out Any>).consumeNextWith { response: Any ->
        testUtils.assertUpdated(response)
      }.verifyComplete()
    }
  }
}
