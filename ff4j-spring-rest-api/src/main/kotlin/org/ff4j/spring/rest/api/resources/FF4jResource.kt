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
import org.ff4j.services.FF4jServices
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_UID
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.domain.AuthorizationsManagerApiBean
import org.ff4j.services.domain.FF4jStatusApiBean
import org.ff4j.services.exceptions.FeatureNotFoundException
import org.ff4j.web.FF4jWebConstants.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "FF4J", description = "The API for global status of FF4J")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}"])
class FF4jResource(@Autowired val ff4JServices: FF4jServices) {

  @Operation(
    summary = "Gets ff4j status overview",
    description = "Gets information related to Monitoring, Security, Cache and Store",
    tags = ["FF4J"]
  )
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Success, return status of ff4j instance",
      content = arrayOf(Content(schema = Schema(implementation = FF4jStatusApiBean::class)))
    )]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getStatus(): ResponseEntity<Mono<FF4jStatusApiBean>> = ResponseEntity.ok(ff4JServices.getStatus())

  @Operation(
    summary = "Gets Security information (permissions manager)",
    description = "Security is implemented through dedicated AuthorizationsManager but it's not mandatory",
    tags = ["FF4J"]
  )
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Status of current ff4j security bean",
      content = arrayOf(Content(schema = Schema(implementation = AuthorizationsManagerApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "no security has been defined")]
  )
  @GetMapping(value = [("/$RESOURCE_SECURITY")], produces = [APPLICATION_JSON_VALUE])
  fun getSecurityInfo(): ResponseEntity<Mono<AuthorizationsManagerApiBean>> =
    ResponseEntity.ok(ff4JServices.getSecurityInfo())

  @Operation(summary = "Simple check feature toggle", tags = ["FF4J"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "If feature is flipped",
      content = arrayOf(Content(schema = Schema(implementation = Boolean::class)))
    ), ApiResponse(responseCode = "404", description = "Feature not found")]
  )
  @GetMapping(value = [("/$OPERATION_CHECK/$PATH_PARAM_UID")])
  fun check(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Mono<Boolean>> {
    val status = ff4JServices.check(featureUID)
    return ResponseEntity.ok(status)
  }

  @Operation(summary = "Advanced check feature toggle (parametrized)", tags = ["FF4J"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "If feature is flipped",
      content = arrayOf(Content(schema = Schema(implementation = Boolean::class)))
    ), ApiResponse(responseCode = "400", description = "Invalid parameter"), ApiResponse(
      responseCode = "404", description = "Feature not found"
    )]
  )
  @PostMapping(
    value = [("/$OPERATION_CHECK/$PATH_PARAM_UID")], consumes = [APPLICATION_FORM_URLENCODED_VALUE]
  )
  fun check(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @RequestParam formParams: MultiValueMap<String, String>
  ): ResponseEntity<Mono<Boolean>> {
    val map = formParams.toSingleValueMap()
    val status = ff4JServices.check(featureUID, map)
    return ResponseEntity.ok(status)
  }

  @Operation(summary = "Check feature toggles", tags = ["FF4J"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Map of featureUId/flipped",
      content = arrayOf(Content(schema = Schema(implementation = Map::class)))
    ), ApiResponse(responseCode = "400", description = "Invalid parameter")]
  )
  @PostMapping(value = [("/$OPERATION_CHECK")])
  fun check(
    @RequestBody featureUIDs: Set<String>,
    @RequestParam formParams: MultiValueMap<String, String>
  ): ResponseEntity<Mono<Map<String, Boolean>>> {
    val featureUIDToEnableMap = HashMap<String, Boolean>()
    for (featureUID in featureUIDs) {
      try {
        //TODO: remove the blocking call
        val status = ff4JServices.check(featureUID, formParams.toSingleValueMap()).block()
        featureUIDToEnableMap[featureUID] = status
      } catch (e: FeatureNotFoundException) {
        featureUIDToEnableMap[featureUID] = false
      }
    }
    return ResponseEntity.ok(Mono.just(featureUIDToEnableMap))
  }
}
