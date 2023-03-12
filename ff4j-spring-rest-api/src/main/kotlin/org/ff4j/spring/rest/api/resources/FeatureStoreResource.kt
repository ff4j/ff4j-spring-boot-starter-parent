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
import org.ff4j.services.FeatureStoreServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_CLEAR_CACHE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.RESOURCE_STORE
import org.ff4j.services.domain.CacheApiBean
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.domain.FeatureStoreApiBean
import org.ff4j.services.domain.GroupDescApiBean
import org.ff4j.web.FF4jWebConstants.*
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
@Tag(name = "FeatureStore", description = "The API for accessing the store of all features")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}$RESOURCE_STORE"])
class FeatureStoreResource(@Autowired val featureStoreService: FeatureStoreServices) {

  @Operation(summary = "Displays information regarding the FeaturesStore", tags = ["FeatureStore"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "OK",
      content = arrayOf(Content(schema = Schema(implementation = FeatureApiBean::class)))
    )]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getFeatureStore(): ResponseEntity<Mono<FeatureStoreApiBean>> =
    ResponseEntity.ok(featureStoreService.getFeatureStore())

  @Operation(summary = "Displays all the Features", tags = ["FeatureStore"])
  @GetMapping(value = [("/$RESOURCE_FEATURES")], produces = [APPLICATION_JSON_VALUE])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "get all features",
      content = arrayOf(Content(schema = Schema(implementation = FeatureApiBean::class)))
    )]
  )
  fun getAllFeatures(): ResponseEntity<Flux<FeatureApiBean>> =
    ResponseEntity.ok(featureStoreService.getAllFeatures())

  @Operation(summary = "Display information regarding Groups", tags = ["FeatureStore"])
  @GetMapping(value = [("/$RESOURCE_GROUPS")], produces = [APPLICATION_JSON_VALUE])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Groups resource",
      content = arrayOf(Content(schema = Schema(implementation = GroupDescApiBean::class)))
    )]
  )
  fun getAllGroups(): ResponseEntity<Flux<GroupDescApiBean>> =
    ResponseEntity.ok(featureStoreService.getAllGroups())

  @Operation(summary = "Display information related to Cache", tags = ["FeatureStore"])
  @GetMapping(value = [("/$RESOURCE_CACHE")], produces = [APPLICATION_JSON_VALUE])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Gets the cached features",
      content = arrayOf(Content(schema = Schema(implementation = CacheApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "feature store is not cached")]
  )
  fun getFeaturesFromCache(): ResponseEntity<Mono<CacheApiBean>> =
    ResponseEntity.ok(featureStoreService.getFeaturesFromCache())

  @Operation(summary = "Delete all Features in store", tags = ["FeatureStore"])
  @DeleteMapping(value = [("/$STORE_CLEAR")])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "204", description = "all feature have been deleted"
    )]
  )
  fun deleteAllFeatures(): ResponseEntity<Mono<Void>> {
    featureStoreService.deleteAllFeatures()
    return ResponseEntity.status(NO_CONTENT).build()
  }

  @Operation(summary = "Clear cache", tags = ["FeatureStore"])
  @DeleteMapping(value = [(RESOURCE_CLEAR_CACHE)])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "204", description = "Gcache is cleared"
    ), ApiResponse(responseCode = "404", description = "feature store is not cached")]
  )
  fun clearCachedFeatureStore(): ResponseEntity<Mono<Void>> {
    featureStoreService.clearCachedFeatureStore()
    return ResponseEntity.status(NO_CONTENT).build()
  }
}
