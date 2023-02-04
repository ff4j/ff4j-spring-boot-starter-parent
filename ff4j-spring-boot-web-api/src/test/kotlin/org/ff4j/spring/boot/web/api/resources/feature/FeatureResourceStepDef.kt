package org.ff4j.spring.boot.web.api.resources.feature

import com.google.gson.Gson
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.ff4j.FF4j
import org.ff4j.core.Feature
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.spring.boot.web.api.FF4JTestHelperUtils
import org.ff4j.spring.boot.web.api.InitializerStepDef
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.context.WebApplicationContext
import java.lang.Boolean
import kotlin.Int
import kotlin.String

class FeatureResourceStepDef(ff4j: FF4j, context: WebApplicationContext) : En {

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
    Given("the feature with {string}, {string}, {string}, {string} and {string} exists in the feature store") { uid: String, enabled: String, description: String, group: String, permissions: String ->
      val feature =
        Feature(uid, Boolean.valueOf(enabled), description, group, permissions.split(","))
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
    Then("the user gets an error response with code {string} and error message as {string}") { responseCode: String, responseBody: String ->
      responseSpec = requestBodySpec.exchange()
      responseSpec
        .expectStatus().isEqualTo(responseCode.toInt())
        .expectBody<String>().isEqualTo(responseBody)
    }
    Then("the user gets the response with response code as {int} and content as {string}, {string}, {string}, {string} and {string}") { responseCode: Int, expectedUid: String, expectedEnabled: String, expectedDescription: String, expectedGroup: String, expectedPermissions: String ->
      val expectedFeature = Feature(
        expectedUid,
        Boolean.valueOf(expectedEnabled),
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