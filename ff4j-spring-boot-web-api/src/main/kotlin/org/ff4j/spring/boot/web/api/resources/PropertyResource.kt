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
import org.ff4j.services.PropertyServices
import org.ff4j.services.constants.FeatureConstants.PARAM_NAME
import org.ff4j.services.constants.FeatureConstants.PARAM_VALUE
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_NAME
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_VALUE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_PROPERTIES_STORE_PROPERTIES
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.spring.boot.web.api.utils.FeatureWebUtils
import org.ff4j.web.FF4jWebConstants.OPERATION_UPDATE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Api(tags = ["Property"], description = "The API for property related operations")
@RestController
@RequestMapping(value = [("$RESOURCE_PROPERTIES_STORE_PROPERTIES/$PATH_PARAM_NAME")])
class PropertyResource(@Autowired val propertyServices: PropertyServices) {

    @ApiOperation(value = "Read information about a property", response = PropertyApiBean::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Information about property"),
            ApiResponse(code = 404, message = "Property not found"))
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getProperty(@PathVariable(value = PARAM_NAME) propertyName: String): PropertyApiBean =
            propertyServices.getProperty(propertyName)

    @ApiOperation(value = "Create or update a property", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 400, message = "Property name is blank (or) property name did not match with the requested property name to be created or updated"),
            ApiResponse(code = 201, message = "Property has been created"),
            ApiResponse(code = 202, message = "Property has been updated"),
            ApiResponse(code = 204, message = "No content, no changes made to the feature"))
    @PutMapping(produces = [APPLICATION_JSON_VALUE])
    fun createOrUpdateProperty(@PathVariable(value = PARAM_NAME) propertyName: String, @RequestBody propertyApiBean: PropertyApiBean): ResponseEntity<*> =
            FeatureWebUtils.getBooleanResponseEntityByHttpStatus(propertyServices.createOrUpdateProperty(propertyName, propertyApiBean))

    @ApiOperation(value = "Delete a property", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 204, message = "No content, property is deleted"),
            ApiResponse(code = 404, message = "Property not found"))
    @DeleteMapping(produces = [APPLICATION_JSON_VALUE])
    fun deleteProperty(@PathVariable(value = PARAM_NAME) propertyName: String): ResponseEntity<Any> {
        propertyServices.deleteProperty(propertyName)
        return ResponseEntity(NO_CONTENT)
    }

    @ApiOperation(value = "Update value of a property", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 202, message = "Property has been updated"),
            ApiResponse(code = 404, message = "Property not found"),
            ApiResponse(code = 400, message = "Invalid new value"))
    @PostMapping(value = [("/$OPERATION_UPDATE/$PATH_PARAM_VALUE")], produces = [APPLICATION_JSON_VALUE])
    fun updatePropertyName(@PathVariable(value = PARAM_NAME) propertyName: String, @PathVariable(value = PARAM_VALUE) newPropertyName: String): ResponseEntity<Any> {
        propertyServices.updatePropertyName(propertyName, newPropertyName)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }
}
