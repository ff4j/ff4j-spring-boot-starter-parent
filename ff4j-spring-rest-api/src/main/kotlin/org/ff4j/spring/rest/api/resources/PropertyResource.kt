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
import org.ff4j.services.PropertyServices
import org.ff4j.services.constants.FeatureConstants.PARAM_NAME
import org.ff4j.services.constants.FeatureConstants.PARAM_VALUE
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_NAME
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_VALUE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.RESOURCE_PROPERTIES
import org.ff4j.services.constants.FeatureConstants.RESOURCE_PROPERTY_STORE
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.web.FF4jWebConstants.OPERATION_UPDATE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "Property", description = "The API for property related operations")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}$RESOURCE_PROPERTY_STORE$RESOURCE_PROPERTIES/$PATH_PARAM_NAME"])
class PropertyResource(@Autowired val propertyServices: PropertyServices) {

  @Operation(summary = "Read information about a property", tags = ["Property"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Information about property",
      content = arrayOf(Content(schema = Schema(implementation = PropertyApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "Property not found")]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getProperty(@PathVariable(value = PARAM_NAME) propertyName: String): ResponseEntity<Mono<PropertyApiBean>> =
    ResponseEntity.ok(propertyServices.getProperty(propertyName))

  @Operation(summary = "Create or update a property", tags = ["Property"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "201", description = "Property has been created"
    ), ApiResponse(responseCode = "202", description = "Property has been updated"), ApiResponse(
      responseCode = "400",
      description = "Property name is blank (or) property name did not match with the requested property name to be created or updated"
    )]
  )
  @PutMapping(produces = [APPLICATION_JSON_VALUE])
  fun createOrUpdateProperty(
    @PathVariable(value = PARAM_NAME) propertyName: String,
    @RequestBody propertyApiBean: PropertyApiBean
  ): ResponseEntity<Mono<Boolean>> =
    // TODO: remove the blocking call
    org.ff4j.spring.rest.api.utils.FeatureWebUtils.getBooleanResponseEntityByHttpStatus(
      propertyServices.createOrUpdateProperty(propertyName, propertyApiBean).block()
    )

  @Operation(summary = "Delete a property", tags = ["Property"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "204", description = "No content, property is deleted"
    ), ApiResponse(responseCode = "404", description = "Property not found")]
  )
  @DeleteMapping(produces = [APPLICATION_JSON_VALUE])
  fun deleteProperty(@PathVariable(value = PARAM_NAME) propertyName: String): ResponseEntity<Mono<Void>> {
    propertyServices.deleteProperty(propertyName)
    return ResponseEntity.status(NO_CONTENT).build()
  }

  @Operation(summary = "Update value of a property", tags = ["Property"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "202", description = "Property has been updated"
    ), ApiResponse(responseCode = "404", description = "Property not found"), ApiResponse(
      responseCode = "400", description = "Invalid new value"
    )]
  )
  @PostMapping(
    value = [("/$OPERATION_UPDATE/$PATH_PARAM_VALUE")], produces = [APPLICATION_JSON_VALUE]
  )
  fun updatePropertyName(
    @PathVariable(value = PARAM_NAME) propertyName: String,
    @PathVariable(value = PARAM_VALUE) newPropertyName: String
  ): ResponseEntity<Mono<Void>> {
    propertyServices.updatePropertyName(propertyName, newPropertyName)
    return ResponseEntity.status(ACCEPTED).build()
  }
}
