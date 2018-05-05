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


import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.ff4j.services.FeatureStoreServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_CLEAR_CACHE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J_STORE
import org.ff4j.services.constants.FeatureConstants.ROOT
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

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@RestController
@RequestMapping(value = [RESOURCE_FF4J_STORE])
class FeatureStoreResource(@Autowired val featureStoreService: FeatureStoreServices) {

    val featureStore: FeatureStoreApiBean
        @GetMapping(produces = [APPLICATION_JSON_VALUE])
        @ApiOperation(value = "Displays information regarding the <b>FeaturesStore</b>", response = FeatureStoreApiBean::class)
        @ApiResponses(ApiResponse(code = 200, message = "status of current feature store", response = FeatureStoreApiBean::class))
        get() = featureStoreService.getFeatureStore()

    val allFeatures: Collection<FeatureApiBean>
        @GetMapping(value = [(ROOT + RESOURCE_FEATURES)], produces = [APPLICATION_JSON_VALUE])
        @ApiOperation(value = "Displays all the <b>Features</b>", response = FeatureApiBean::class)
        @ApiResponses(ApiResponse(code = 200, message = "get all features"))
        get() = featureStoreService.getAllFeatures()

    val allGroups: Collection<GroupDescApiBean>
        @GetMapping(value = [(ROOT + RESOURCE_GROUPS)], produces = [APPLICATION_JSON_VALUE])
        @ApiOperation(value = "Display information regarding <b>Groups</b>", response = GroupDescApiBean::class)
        @ApiResponses(ApiResponse(code = 200, message = "Groups resource", response = GroupDescApiBean::class))
        get() = featureStoreService.getAllGroups()

    val featuresFromCache: CacheApiBean
        @GetMapping(value = [(ROOT + RESOURCE_CACHE)], produces = [APPLICATION_JSON_VALUE])
        @ApiOperation(value = "Display information related to <b>Cache</b>")
        @ApiResponses(ApiResponse(code = 200, message = "Gets the cached features", response = CacheApiBean::class), ApiResponse(code = 404, message = "feature store is not cached"))
        get() = featureStoreService.getFeaturesFromCache()

    @DeleteMapping(value = [(ROOT + STORE_CLEAR)])
    @ApiOperation(value = "Delete all <b>Features</b> in store")
    @ApiResponses(ApiResponse(code = 204, message = "all feature have been deleted"))
    fun deleteAllFeatures(): ResponseEntity<Any> {
        featureStoreService.deleteAllFeatures()
        return ResponseEntity(NO_CONTENT)
    }

    @DeleteMapping(value = [(ROOT + RESOURCE_CLEAR_CACHE)])
    @ApiOperation(value = "Clear cache", response = ResponseEntity::class)
    @ApiResponses(ApiResponse(code = 204, message = "cache is cleared"), ApiResponse(code = 404, message = "feature store is not cached"))
    fun clearCachedFeatureStore(): ResponseEntity<Any> {
        featureStoreService.clearCachedFeatureStore()
        return ResponseEntity(NO_CONTENT)
    }
}
