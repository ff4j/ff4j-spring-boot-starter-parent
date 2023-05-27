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
package org.ff4j.spring.rest.api.resources.feature

import com.google.gson.Gson
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.spring.rest.api.InitializerStepDef
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.context.WebApplicationContext

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class FeatureResourceStepDef(ff4j: FF4j, context: WebApplicationContext) : En {

  private val testUtils = org.ff4j.spring.rest.api.FF4JTestHelperUtils(ff4j)
  private lateinit var requestBodySpec: WebTestClient.RequestBodySpec
  private lateinit var responseSpec: WebTestClient.ResponseSpec
  private lateinit var webTestClient: WebTestClient

  init {
    Before { _ -> webTestClient = InitializerStepDef().getTestClient(context) }

    InitializerStepDef().initDataTable()

    Given("the feature store is cleared") {
      testUtils.clearFeatureStore()
    }
    Given("the feature with {string}, {string}, {string}, {string} and {string} exists in the feature store") { uid: String, enabled: String, description: String, group: String, permissions: String ->
      val feature =
        Feature(uid, enabled.toBooleanStrict(), description, group, permissions.split(","))
      testUtils.createFeatures(listOf(feature))
    }
    Given("the following features exists in the feature store") { dataTable: DataTable ->
      val features = dataTable.asList(Feature::class.java)
      testUtils.createFeatures(features)
    }
    When("the user requests for a feature by {string} by {string} http method and content type as {string}") { uri: String, httpMethod: String, contentType: String ->
      requestBodySpec = webTestClient.method(HttpMethod.valueOf(httpMethod))
        .uri(uri)
        .contentType(MediaType.valueOf(contentType))
    }
    When("the user requests for a feature by {string} appended with {string} by {string} http method and content type as {string}") { uri: String, appendToPath: String, httpMethod: String, contentType: String ->
      requestBodySpec = webTestClient.method(HttpMethod.valueOf(httpMethod))
        .uri("$uri$appendToPath")
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
    Then("the user gets the response with response code as {int} and content as {string}, {string}, {string}, {string} and {string}") { responseCode: Int, expectedUid: String, expectedEnabled: String, expectedDescription: String, expectedGroup: String, expectedPermissions: String ->
      val expectedFeature = Feature(
        expectedUid,
        expectedEnabled.toBooleanStrict(),
        expectedDescription,
        expectedGroup,
        expectedPermissions.split(","),
      )
      val expectedFeatureApiBean = FeatureApiBean(expectedFeature)
      responseSpec = requestBodySpec.exchange()
      responseSpec
        .expectStatus().isEqualTo(responseCode)
        .expectBody().json(Gson().toJson(expectedFeatureApiBean))
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
