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
import org.ff4j.services.FeatureServices
import org.ff4j.services.constants.FeatureConstants.PARAM_GROUP
import org.ff4j.services.constants.FeatureConstants.PARAM_ROLE
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_GROUP
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_ROLE
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_UID
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FEATURES
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.RESOURCE_STORE
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.model.FeatureActions
import org.ff4j.spring.rest.api.utils.FeatureWebUtils
import org.ff4j.web.FF4jWebConstants.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "Feature", description = "The API for feature related operations")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}$RESOURCE_STORE$RESOURCE_FEATURES/$PATH_PARAM_UID"])
class FeatureResource(@Autowired val featureServices: FeatureServices) {

  @Operation(summary = "Get feature by uid", description = "Get feature by uid", tags = ["Feature"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "OK",
      content = arrayOf(Content(schema = Schema(implementation = FeatureApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "Feature not found")]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getFeatureByUID(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Mono<FeatureApiBean>> =
    ResponseEntity.ok(featureServices.getFeature(featureUID))

  @Operation(summary = "Create or update a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(
      responseCode = "400",
      description = "Feature uid is blank (or) feature uid did not match with the requested feature uid to be created or updated"
    ), ApiResponse(
      responseCode = "201",
      description = "Feature has been created",
      content = arrayOf(Content(schema = Schema(implementation = Boolean::class)))
    ), ApiResponse(
      responseCode = "204",
      description = "Feature has been updated",
      content = arrayOf(Content(schema = Schema(implementation = Boolean::class)))
    )
  )
  @PutMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
  fun createOrUpdateFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @RequestBody featureApiBean: FeatureApiBean
  ): ResponseEntity<Mono<Boolean>> {
    //TODO: remove the blocking call
    val featureAction: FeatureActions = featureServices.createOrUpdateFeature(
      featureUID, featureApiBean
    ).block()
    return FeatureWebUtils.getBooleanResponseEntityByHttpStatus(featureAction)
  }

  @Operation(summary = "Delete a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "204", description = "No content, feature is deleted"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @DeleteMapping
  fun deleteFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Void> {
    featureServices.deleteFeature(featureUID)
    return ResponseEntity.noContent().build()
  }

  @Operation(summary = "Enable a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Features has been enabled"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_ENABLE")])
  fun enableFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Void> {
    featureServices.enableFeature(featureUID)
    return ResponseEntity.status(ACCEPTED).build()
  }

  @Operation(summary = "Disable a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Features has been disabled"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_DISABLE")])
  fun disableFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Void> {
    featureServices.disableFeature(featureUID)
    return ResponseEntity.status(ACCEPTED).build()
  }

  @Operation(summary = "Grant a permission to a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Permission has been granted"),
    ApiResponse(responseCode = "404", description = "Feature not found"),
    ApiResponse(responseCode = "304", description = "Role already exists, nothing to update")
  )
  @PostMapping(value = [("/$OPERATION_GRANTROLE/$PATH_PARAM_ROLE")])
  fun grantRoleToFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @PathVariable(value = PARAM_ROLE) role: String
  ): ResponseEntity<Void> {
    featureServices.grantRoleToFeature(featureUID, role)
    return ResponseEntity.status(ACCEPTED).build()
  }

  @Operation(summary = "Revoke a permission from a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Permission has been granted"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_REMOVEROLE/$PATH_PARAM_ROLE")])
  fun removeRoleFromFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @PathVariable(value = PARAM_ROLE) role: String
  ): ResponseEntity<Void> {
    featureServices.removeRoleFromFeature(featureUID, role)
    return ResponseEntity.status(ACCEPTED).build()
  }

  @Operation(summary = "Define the group of the feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Group has been defined"),
    ApiResponse(responseCode = "404", description = "Feature not found"),
    ApiResponse(responseCode = "304", description = "Group already exists, nothing to update")
  )
  @PostMapping(value = [("/$OPERATION_ADDGROUP/$PATH_PARAM_GROUP")])
  fun addGroupToFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @PathVariable(value = PARAM_GROUP) groupName: String
  ): ResponseEntity<Void> {
    featureServices.addGroupToFeature(featureUID, groupName)
    return ResponseEntity.status(ACCEPTED).build()
  }

  @Operation(summary = "Remove the group of the feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Group has been removed"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_REMOVEGROUP/$PATH_PARAM_GROUP")])
  fun removeGroupFromFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @PathVariable(value = PARAM_GROUP) groupName: String
  ): ResponseEntity<Void> {
    featureServices.removeGroupFromFeature(featureUID, groupName)
    return ResponseEntity.status(ACCEPTED).build()
  }
}
