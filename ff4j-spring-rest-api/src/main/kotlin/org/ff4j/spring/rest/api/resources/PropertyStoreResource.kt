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

package org.ff4j.spring.rest.api.resources

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.ff4j.services.PropertyStoreServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_CLEAR_CACHE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.RESOURCE_PROPERTIES
import org.ff4j.services.constants.FeatureConstants.RESOURCE_PROPERTY_STORE
import org.ff4j.services.domain.CacheApiBean
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.services.domain.PropertyStoreApiBean
import org.ff4j.web.FF4jWebConstants.RESOURCE_CACHE
import org.ff4j.web.FF4jWebConstants.STORE_CLEAR
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "PropertyStore", description = "The API for accessing the store for all properties")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}$RESOURCE_PROPERTY_STORE"])
class PropertyStoreResource(@Autowired val propertyStoreServices: PropertyStoreServices) {

  @Operation(summary = "Display information regarding Properties Store", tags = ["PropertyStore"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "status of current properties store",
      content = arrayOf(Content(schema = Schema(implementation = PropertyStoreApiBean::class)))
    )]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getPropertyStore(): ResponseEntity<Mono<PropertyStoreApiBean>> =
    ResponseEntity.ok(propertyStoreServices.getPropertyStore())

  @Operation(summary = "Display all the Properties", tags = ["PropertyStore"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "get all Properties",
      content = arrayOf(Content(schema = Schema(implementation = PropertyApiBean::class)))
    )]
  )
  @GetMapping(value = [RESOURCE_PROPERTIES], produces = [APPLICATION_JSON_VALUE])
  fun getAllProperties(): ResponseEntity<Flux<PropertyApiBean>> =
    ResponseEntity.ok(propertyStoreServices.getAllProperties())

  @Operation(summary = "Display information related to Cache", tags = ["PropertyStore"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Gets the cached properties",
      content = arrayOf(Content(schema = Schema(implementation = CacheApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "property store is not cached")]
  )
  @GetMapping(value = [("/$RESOURCE_CACHE")], produces = [APPLICATION_JSON_VALUE])
  fun getPropertiesFromCache(): ResponseEntity<Mono<CacheApiBean>> =
    ResponseEntity.ok(propertyStoreServices.getPropertiesFromCache())

  @Operation(summary = "Delete all Properties in store", tags = ["PropertyStore"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "204", description = "all properties have been deleted"
    )]
  )
  @DeleteMapping(value = [("/$STORE_CLEAR")])
  fun deleteAllProperties(): ResponseEntity<Mono<Void>> {
    propertyStoreServices.deleteAllProperties()
    return ResponseEntity.status(NO_CONTENT).build()
  }

  @Operation(summary = "Clear cache", tags = ["PropertyStore"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "204", description = "cache is cleared"
    ), ApiResponse(responseCode = "404", description = "property store is not cached")]
  )
  @DeleteMapping(value = [RESOURCE_CLEAR_CACHE])
  fun clearCachedPropertyStore(): ResponseEntity<Mono<Void>> {
    propertyStoreServices.clearCachedPropertyStore()
    return ResponseEntity.status(NO_CONTENT).build()
  }
}
