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
import org.ff4j.services.PropertyStoreServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_CLEAR_CACHE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J_PROPERTY_STORE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_PROPERTIES
import org.ff4j.services.constants.FeatureConstants.ROOT
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

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Api(tags = ["PropertyStore"], description = "The API for accessing the store for all properties")
@RestController
@RequestMapping(value = [RESOURCE_FF4J_PROPERTY_STORE])
class PropertyStoreResource(@Autowired val propertyStoreServices: PropertyStoreServices) {

    val propertyStore: PropertyStoreApiBean
        @ApiOperation(value = "Display information regarding Properties Store", response = PropertyStoreApiBean::class)
        @ApiResponses(ApiResponse(code = 200, message = "status of current properties store"))
        @GetMapping(produces = [APPLICATION_JSON_VALUE])
        get() = propertyStoreServices.getPropertyStore()

    val allProperties: List<PropertyApiBean>
        @ApiOperation(value = "Display all the Properties", response = PropertyApiBean::class)
        @ApiResponses(ApiResponse(code = 200, message = "get all Properties"))
        @GetMapping(value = [RESOURCE_PROPERTIES], produces = [APPLICATION_JSON_VALUE])
        get() = propertyStoreServices.getAllProperties()

    val propertiesFromCache: CacheApiBean
        @ApiOperation(value = "Display information related to Cache")
        @ApiResponses(
                ApiResponse(code = 200, message = "Gets the cached properties", response = CacheApiBean::class),
                ApiResponse(code = 404, message = "property store is not cached"))
        @GetMapping(value = [(ROOT + RESOURCE_CACHE)], produces = [APPLICATION_JSON_VALUE])
        get() = propertyStoreServices.getPropertiesFromCache()

    @ApiOperation(value = "Delete all Properties in store")
    @ApiResponses(ApiResponse(code = 204, message = "all properties have been deleted", response = ResponseEntity::class))
    @DeleteMapping(value = [(ROOT + STORE_CLEAR)])
    fun deleteAllProperties(): ResponseEntity<Any> {
        propertyStoreServices.deleteAllProperties()
        return ResponseEntity(NO_CONTENT)
    }

    @ApiOperation(value = "Clear cache", response = ResponseEntity::class)
    @ApiResponses(
            ApiResponse(code = 204, message = "cache is cleared"),
            ApiResponse(code = 404, message = "property store is not cached"))
    @DeleteMapping(value = [RESOURCE_CLEAR_CACHE])
    fun clearCachedPropertyStore(): ResponseEntity<Any> {
        propertyStoreServices.clearCachedPropertyStore()
        return ResponseEntity(NO_CONTENT)
    }
}
