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
import org.ff4j.services.exceptions.FeatureNotFoundException
import org.ff4j.services.FF4jServices
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_UID
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.domain.AuthorizationsManagerApiBean
import org.ff4j.services.domain.FF4jStatusApiBean
import org.ff4j.web.FF4jWebConstants.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Api(tags = ["FF4J"], description = "The API for global status of FF4J")
@RestController
@RequestMapping(value = [RESOURCE_FF4J])
class FF4jResource(@Autowired val ff4JServices: FF4jServices) {

    @ApiOperation(value = "Gets ff4j status overview", notes = "Gets information related to Monitoring, Security, Cache and Store")
    @ApiResponses(
            ApiResponse(code = 200, message = "Success, return status of ff4j instance", response = FF4jStatusApiBean::class))
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getStatus(): FF4jStatusApiBean = ff4JServices.getStatus()

    @ApiOperation(value = "Gets Security information (permissions manager)", notes = "Security is implemented through dedicated AuthorizationsManager but it's not mandatory")
    @ApiResponses(
            ApiResponse(code = 200, message = "Status of current ff4j security bean", response = AuthorizationsManagerApiBean::class),
            ApiResponse(code = 404, message = "no security has been defined"))
    @GetMapping(value = [("/$RESOURCE_SECURITY")], produces = [APPLICATION_JSON_VALUE])
    fun getSecurityInfo(): AuthorizationsManagerApiBean = ff4JServices.getSecurityInfo()


    @ApiOperation(value = "Simple check feature toggle", response = Boolean::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "If feature is flipped"),
            ApiResponse(code = 404, message = "Feature not found"))
    @GetMapping(value = [("/$OPERATION_CHECK/$PATH_PARAM_UID")])
    fun check(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Boolean> {
        val status = ff4JServices.check(featureUID)
        return ResponseEntity(status, OK)
    }

    @ApiOperation(value = "Advanced check feature toggle (parametrized)", response = Boolean::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "If feature is flipped"),
            ApiResponse(code = 400, message = "Invalid parameter"),
            ApiResponse(code = 404, message = "Feature not found"))
    @PostMapping(value = [("/$OPERATION_CHECK/$PATH_PARAM_UID")], consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun check(@PathVariable(value = PARAM_UID) featureUID: String, @RequestParam formParams: MultiValueMap<String, String>): ResponseEntity<Boolean> {
        val map = formParams.toSingleValueMap()
        val status = ff4JServices.check(featureUID, map)
        return ResponseEntity(status, OK)
    }

    @ApiOperation(value = "Check feature toggles", response = Map::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Map of featureUId/flipped"),
            ApiResponse(code = 400, message = "Invalid parameter"))
    @PostMapping(value = [("/$OPERATION_CHECK")])
    fun check(@RequestBody featureUIDs: Set<String>): ResponseEntity<Map<String,Boolean>> {
        val featureUIDToEnableMap = HashMap<String, Boolean>()
        for (featureUID in featureUIDs) {
            try {
                val status = ff4JServices.check(featureUID)
                featureUIDToEnableMap[featureUID] = status
            } catch (e : FeatureNotFoundException) {
                featureUIDToEnableMap[featureUID] = false
            }
        }
        return ResponseEntity(featureUIDToEnableMap, OK)
    }
}
