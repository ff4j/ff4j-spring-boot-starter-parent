/*-
 * #%L
 * ff4j-spring-boot-web-api
 * %%
 * Copyright (C) 2013 - 2019 FF4J
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

package org.ff4j.spring.boot.web.api.resources

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
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J_STORE_FEATURES
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.spring.boot.web.api.utils.FeatureWebUtils
import org.ff4j.web.FF4jWebConstants.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "Feature", description = "The API for feature related operations")
@RestController
@RequestMapping(value = [("$RESOURCE_FF4J_STORE_FEATURES/$PATH_PARAM_UID")])
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
  fun getFeatureByUID(@PathVariable(value = PARAM_UID) featureUID: String): FeatureApiBean =
    featureServices.getFeature(featureUID)

  @Operation(summary = "Create or update a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(
      responseCode = "400",
      description = "Feature uid is blank (or) feature uid did not match with the requested feature uid to be created or updated"
    ),
    ApiResponse(
      responseCode = "201",
      description = "Feature has been created",
      content = arrayOf(Content(schema = Schema(implementation = Boolean::class)))
    ),
    ApiResponse(
      responseCode = "204",
      description = "Feature has been updated",
      content = arrayOf(Content(schema = Schema(implementation = Boolean::class)))
    )
  )
  @PutMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
  fun createOrUpdateFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @RequestBody featureApiBean: FeatureApiBean
  ): ResponseEntity<Boolean> = FeatureWebUtils.getBooleanResponseEntityByHttpStatus(
    featureServices.createOrUpdateFeature(
      featureUID,
      featureApiBean
    )
  )

  @Operation(summary = "Delete a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "204", description = "No content, feature is deleted"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @DeleteMapping
  fun deleteFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Void> {
    featureServices.deleteFeature(featureUID)
    return ResponseEntity(NO_CONTENT)
  }

  @Operation(summary = "Enable a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Features has been enabled"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_ENABLE")])
  fun enableFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Void> {
    featureServices.enableFeature(featureUID)
    return ResponseEntity(ACCEPTED)
  }

  @Operation(summary = "Disable a feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "202", description = "Features has been disabled"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_DISABLE")])
  fun disableFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Void> {
    featureServices.disableFeature(featureUID)
    return ResponseEntity(ACCEPTED)
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
    return ResponseEntity(ACCEPTED)
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
    return ResponseEntity(ACCEPTED)
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
    return ResponseEntity(ACCEPTED)
  }

  @Operation(summary = "Remove the group of the feature", tags = ["Feature"])
  @ApiResponses(
    ApiResponse(responseCode = "204", description = "Group has been removed"),
    ApiResponse(responseCode = "404", description = "Feature not found")
  )
  @PostMapping(value = [("/$OPERATION_REMOVEGROUP/$PATH_PARAM_GROUP")])
  fun removeGroupFromFeature(
    @PathVariable(value = PARAM_UID) featureUID: String,
    @PathVariable(value = PARAM_GROUP) groupName: String
  ): ResponseEntity<Void> {
    featureServices.removeGroupFromFeature(featureUID, groupName)
    return ResponseEntity(ACCEPTED)
  }
}