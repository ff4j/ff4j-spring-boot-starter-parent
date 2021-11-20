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


import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.ff4j.services.FeatureStoreServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_CLEAR_CACHE
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J_STORE
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
@Api(tags = ["FeatureStore"], description = "The API for accessing the store of all features")
@RestController
@RequestMapping(value = [RESOURCE_FF4J_STORE])
class FeatureStoreResource(@Autowired val featureStoreService: FeatureStoreServices) {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Displays information regarding the FeaturesStore", response = FeatureStoreApiBean::class)
    @ApiResponses(ApiResponse(code = 200, message = "status of current feature store", response = FeatureStoreApiBean::class))
    fun getFeatureStore(): FeatureStoreApiBean = featureStoreService.getFeatureStore()

    @GetMapping(value = [("/$RESOURCE_FEATURES")], produces = [APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Displays all the Features", response = FeatureApiBean::class)
    @ApiResponses(ApiResponse(code = 200, message = "get all features"))
    fun getAllFeatures(): Collection<FeatureApiBean> = featureStoreService.getAllFeatures()

    @GetMapping(value = [("/$RESOURCE_GROUPS")], produces = [APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Display information regarding Groups", response = GroupDescApiBean::class)
    @ApiResponses(ApiResponse(code = 200, message = "Groups resource", response = GroupDescApiBean::class))
    fun getAllGroups(): Collection<GroupDescApiBean> = featureStoreService.getAllGroups()

    @GetMapping(value = [("/$RESOURCE_CACHE")], produces = [APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Display information related to Cache")
    @ApiResponses(ApiResponse(code = 200, message = "Gets the cached features", response = CacheApiBean::class), ApiResponse(code = 404, message = "feature store is not cached"))
    fun getFeaturesFromCache(): CacheApiBean = featureStoreService.getFeaturesFromCache()

    @DeleteMapping(value = [("/$STORE_CLEAR")])
    @ApiOperation(value = "Delete all Features in store")
    @ApiResponses(ApiResponse(code = 204, message = "all feature have been deleted"))
    fun deleteAllFeatures(): ResponseEntity<Void> {
        featureStoreService.deleteAllFeatures()
        return ResponseEntity(NO_CONTENT)
    }

    @DeleteMapping(value = [(RESOURCE_CLEAR_CACHE)])
    @ApiOperation(value = "Clear cache", response = ResponseEntity::class)
    @ApiResponses(ApiResponse(code = 204, message = "cache is cleared"), ApiResponse(code = 404, message = "feature store is not cached"))
    fun clearCachedFeatureStore(): ResponseEntity<Void> {
        featureStoreService.clearCachedFeatureStore()
        return ResponseEntity(NO_CONTENT)
    }
}
