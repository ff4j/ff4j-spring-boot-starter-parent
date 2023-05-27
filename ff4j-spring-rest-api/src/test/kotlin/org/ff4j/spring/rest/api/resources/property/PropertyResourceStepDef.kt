/*-
 * #%L
 * ff4j-spring-rest-api
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
package org.ff4j.spring.rest.api.resources.property

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.ff4j.FF4j
import org.ff4j.spring.rest.api.FF4JTestHelperUtils
import org.ff4j.spring.rest.api.InitializerStepDef
import org.ff4j.spring.rest.api.representation.PropertyPojo
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.context.WebApplicationContext

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class PropertyResourceStepDef(ff4j: FF4j, context: WebApplicationContext) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var requestBodySpec: WebTestClient.RequestBodySpec
  private lateinit var responseSpec: WebTestClient.ResponseSpec
  private lateinit var webTestClient: WebTestClient

  init {
    Before { _ -> webTestClient = InitializerStepDef().getTestClient(context) }

    InitializerStepDef().initDataTable()

    Given("the property store is cleared") {
      testUtils.clearPropertyStore()
    }
    Given("the following properties exists in the property store") { dataTable: DataTable ->
      dataTable.asList(PropertyPojo::class.java).map {
        testUtils.convertToPropertyModel(it)
      }.forEach { ff4j.createProperty(it) }
    }
    When("the user requests for a feature by {string} by {string} http method and content type as {string}") { uri: String, httpMethod: String, contentType: String ->
      requestBodySpec = webTestClient.method(HttpMethod.valueOf(httpMethod))
        .uri(uri)
        .contentType(MediaType.valueOf(contentType))
    }
    When("request body as") { requestBody: String ->
      requestBodySpec.bodyValue(requestBody)
    }
    Then("the user gets an error response with code {string} and error message as") { responseCode: String, responseBody: String ->
      responseSpec = requestBodySpec.exchange()
      responseSpec
        .expectStatus().isEqualTo(responseCode.toInt())
        .expectBody().json(responseBody)
    }
    Then("the user gets the response with response code {string}") { responseCode: String ->
      responseSpec = requestBodySpec.exchange()
      responseSpec
        .expectStatus().isEqualTo(responseCode.toInt())
    }
    Then("the response body has content to be {string}") { responseBody: String ->
      responseSpec.expectBody<String>().consumeWith { body ->
        Assertions.assertThat(body.responseBody).isEqualTo(responseBody)
      }
    }
    Then("the response body as") { responseBody: String ->
      responseSpec.expectBody().json(responseBody)
    }
  }
}
