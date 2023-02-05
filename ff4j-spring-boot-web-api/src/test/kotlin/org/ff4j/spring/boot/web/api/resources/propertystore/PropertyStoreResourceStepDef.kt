package org.ff4j.spring.boot.web.api.resources.propertystore

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.ff4j.FF4j
import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.cache.InMemoryCacheManager
import org.ff4j.spring.boot.web.api.FF4JTestHelperUtils
import org.ff4j.spring.boot.web.api.InitializerStepDef
import org.ff4j.spring.boot.web.api.representation.PropertyPojo
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.context.WebApplicationContext

class PropertyStoreResourceStepDef(ff4j: FF4j, context: WebApplicationContext) : En {

  private val testUtils = FF4JTestHelperUtils(ff4j)
  private lateinit var response: WebTestClient.ResponseSpec
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
    Given("the property store is cached") {
      val proxy = FF4jCacheProxy(ff4j.featureStore, ff4j.propertiesStore, InMemoryCacheManager())
      ff4j.propertiesStore = proxy
      ff4j.featureStore = proxy
    }
    Given("the following properties are cached") { dataTable: DataTable ->
      dataTable.asList(PropertyPojo::class.java).map {
        testUtils.convertToPropertyModel(it)
      }.forEach { (ff4j.propertiesStore as FF4jCacheProxy).cacheManager.putProperty(it) }
    }
    When("the user requests for a feature by {string} by {string} http method and content type as {string}") { uri: String, httpMethod: String, contentType: String ->
      response = webTestClient.method(HttpMethod.valueOf(httpMethod))
        .uri(uri)
        .contentType(MediaType.valueOf(contentType))
        .exchange()
    }
    Then("the user gets the response with response code {string}") { responseCode: String ->
      response.expectStatus().isEqualTo(responseCode.toInt())
    }
    Then("the response body as") { responseBody: String ->
      response.expectBody().json(responseBody)
    }
    Then("the user gets an error response with code {string} and error message as {string}") { responseCode: String, responseBody: String ->
      response
        .expectStatus().isEqualTo(responseCode.toInt())
        .expectBody<String>().isEqualTo(responseBody)
    }
  }
}