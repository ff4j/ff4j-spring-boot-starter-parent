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
/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */

package org.ff4j.spring.boot.web.api.resources

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
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
@Api(tags = ["Feature"], description = "The API for Feature related operations")
@RestController
@RequestMapping(value = [("$RESOURCE_FF4J_STORE_FEATURES/$PATH_PARAM_UID")])
class FeatureResource(@Autowired val featureServices: FeatureServices) {

    @ApiOperation(value = "Get feature by uid", response = FeatureApiBean::class)
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getFeatureByUID(@PathVariable(value = PARAM_UID) featureUID: String): FeatureApiBean = featureServices.getFeature(featureUID)

    @ApiOperation(value = "Create or update a feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 400, message = "Feature uid is blank (or) feature uid did not match with the requested feature uid to be created or updated"),
            ApiResponse(code = 201, message = "Feature has been created"),
            ApiResponse(code = 202, message = "Feature has been updated"),
            ApiResponse(code = 204, message = "No content, no changes made to the feature"))
    @PutMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun createOrUpdateFeature(@PathVariable(value = PARAM_UID) featureUID: String, @RequestBody featureApiBean: FeatureApiBean): ResponseEntity<Boolean> =
            FeatureWebUtils.getBooleanResponseEntityByHttpStatus(featureServices.createOrUpdateFeature(featureUID, featureApiBean))

    @ApiOperation(value = "Delete a feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 204, message = "No content, feature is deleted"),
            ApiResponse(code = 404, message = "Feature not found"))
    @DeleteMapping
    fun deleteFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Any> {
        featureServices.deleteFeature(featureUID)
        return ResponseEntity(NO_CONTENT)
    }

    @ApiOperation(value = "Enable a feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 202, message = "Features has been enabled"),
            ApiResponse(code = 404, message = "Feature not found"))
    @PostMapping(value = [("/$OPERATION_ENABLE")], consumes = [APPLICATION_JSON_VALUE])
    fun enableFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Any> {
        featureServices.enableFeature(featureUID)
        return ResponseEntity(ACCEPTED)
    }

    @ApiOperation(value = "Disable a feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 202, message = "Features has been disabled"),
            ApiResponse(code = 404, message = "Feature not found"))
    @PostMapping(value = [("/$OPERATION_DISABLE")], consumes = [APPLICATION_JSON_VALUE])
    fun disableFeature(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Any> {
        featureServices.disableFeature(featureUID)
        return ResponseEntity(ACCEPTED)
    }

    @ApiOperation(value = "Grant a permission to a feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 202, message = "Permission has been granted"),
            ApiResponse(code = 404, message = "Feature not found"),
            ApiResponse(code = 304, message = "Role already exists, nothing to update"))
    @PostMapping(value = [("/$OPERATION_GRANTROLE/$PATH_PARAM_ROLE")], consumes = [APPLICATION_JSON_VALUE])
    fun grantRoleToFeature(@PathVariable(value = PARAM_UID) featureUID: String, @PathVariable(value = PARAM_ROLE) role: String): ResponseEntity<Any> {
        featureServices.grantRoleToFeature(featureUID, role)
        return ResponseEntity(ACCEPTED)
    }

    @ApiOperation(value = "Remove a permission from a feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 202, message = "Permission has been granted"),
            ApiResponse(code = 404, message = "Feature not found"))
    @PostMapping(value = [("/$OPERATION_REMOVEROLE/$PATH_PARAM_ROLE")], consumes = [APPLICATION_JSON_VALUE])
    fun removeRoleFromFeature(@PathVariable(value = PARAM_UID) featureUID: String, @PathVariable(value = PARAM_ROLE) role: String): ResponseEntity<Any> {
        featureServices.removeRoleFromFeature(featureUID, role)
        return ResponseEntity(ACCEPTED)
    }

    @ApiOperation(value = "Define the group of the feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 202, message = "Group has been defined"),
            ApiResponse(code = 404, message = "Feature not found"),
            ApiResponse(code = 304, message = "Group already exists, nothing to update"))
    @PostMapping(value = [("/$OPERATION_ADDGROUP/$PATH_PARAM_GROUP")], consumes = [APPLICATION_JSON_VALUE])
    fun addGroupToFeature(@PathVariable(value = PARAM_UID) featureUID: String, @PathVariable(value = PARAM_GROUP) groupName: String): ResponseEntity<Any> {
        featureServices.addGroupToFeature(featureUID, groupName)
        return ResponseEntity(ACCEPTED)
    }

    @ApiOperation(value = "Remove the group of the feature", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 204, message = "Group has been removed"),
            ApiResponse(code = 404, message = "Feature not found"))
    @PostMapping(value = [("/$OPERATION_REMOVEGROUP/$PATH_PARAM_GROUP")], consumes = [APPLICATION_JSON_VALUE])
    fun removeGroupFromFeature(@PathVariable(value = PARAM_UID) featureUID: String, @PathVariable(value = PARAM_GROUP) groupName: String): ResponseEntity<Any> {
        featureServices.removeGroupFromFeature(featureUID, groupName)
        return ResponseEntity(ACCEPTED)
    }
}
