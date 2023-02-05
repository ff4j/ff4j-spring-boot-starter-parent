/*-
 * #%L
 * ff4j-spring-boot-web-api
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
package org.ff4j.spring.boot.web.api.resources.ff4j

import com.google.gson.Gson
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.spring.boot.web.api.FF4JTestHelperUtils
import org.ff4j.spring.boot.web.api.InitializerStepDef
import org.ff4j.spring.boot.web.api.representation.TestAuthorizationsManager
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.util.UriBuilder
import java.net.URI


class FF4JResourceStepDef(ff4j: FF4j, context: WebApplicationContext) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var requestBodySpec: WebTestClient.RequestBodySpec
  private lateinit var responseSpec: WebTestClient.ResponseSpec
  private lateinit var webTestClient: WebTestClient

  init {
    Before { _ -> webTestClient = InitializerStepDef().getTestClient(context) }

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
    When("the user requests for a feature by {string} by {string} http method and content type as {string}") { uri: String, httpMethod: String, contentType: String ->
      requestBodySpec = webTestClient.method(HttpMethod.valueOf(httpMethod))
        .uri(uri)
        .contentType(MediaType.valueOf(contentType))
    }
    When("request body as") { requestBody: String ->
      requestBodySpec.bodyValue(requestBody)
    }
    When("the user requests for a feature by {string} by {string} http method and content type as {string} and the following form param") { uri: String, httpMethod: String, contentType: String, dataTable: DataTable ->
      val formParams = dataTable.asMap()
      requestBodySpec = webTestClient.method(HttpMethod.valueOf(httpMethod))
        .uri { uriBuilder: UriBuilder -> buildUrl(uriBuilder, uri, formParams) }
        .contentType(MediaType.valueOf(contentType))

    }
    When("the following feature uids") { dataTable: DataTable ->
      val featureUids = dataTable.asList()
      requestBodySpec.bodyValue(Gson().toJson(featureUids))
    }
    Then("the user gets the response with response code {string}") { responseCode: String ->
      responseSpec = requestBodySpec.exchange()
      responseSpec
        .expectStatus().isEqualTo(responseCode.toInt())
    }
    Then("the response body as") { responseBody: String ->
      responseSpec.expectBody().json(responseBody)
    }
    Then("the user gets an error response with code {string} and error message as {string}") { responseCode: String, responseBody: String ->
      responseSpec = requestBodySpec.exchange()
      responseSpec
        .expectStatus().isEqualTo(responseCode.toInt())
        .expectBody<String>().isEqualTo(responseBody)
    }
    Then("the response body has content to be {string}") { responseBody: String ->
      responseSpec.expectBody<String>().consumeWith { body ->
        Assertions.assertThat(body.responseBody).isEqualTo(responseBody)
      }
    }
  }

  private fun buildUrl(
    uriBuilder: UriBuilder,
    path: String,
    formParams: Map<String, String>
  ): URI? {
    formParams.forEach { (name: String?, values: String?) ->
      uriBuilder.queryParam(
        name,
        values
      )
    }
    return uriBuilder.path(path).build()
  }
}
