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
import org.ff4j.services.FF4jServices
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_UID
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.ROOT
import org.ff4j.services.domain.AuthorizationsManagerApiBean
import org.ff4j.services.domain.FF4jStatusApiBean
import org.ff4j.web.FF4jWebConstants.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@RestController
@RequestMapping(value = [RESOURCE_FF4J])
class FF4jResource(@Autowired val ff4JServices: FF4jServices) {

    val status: FF4jStatusApiBean
        @ApiOperation(value = "Gets <b>ff4j</b> status overview", notes = "Gets information related to <b>Monitoring</b>, <b>Security</b>, <b>Cache</b> and <b>Store</b>")
        @ApiResponses(
                ApiResponse(code = 200, message = "Success, return status of ff4j instance", response = FF4jStatusApiBean::class))
        @GetMapping(produces = [APPLICATION_JSON_VALUE])
        get() = ff4JServices.getStatus()

    val securityInfo: AuthorizationsManagerApiBean
        @ApiOperation(value = "Gets <b>Security</b> information (permissions manager)", notes = "Security is implemented through dedicated <b>AuthorizationsManager</b> but it's not mandatory")
        @ApiResponses(
                ApiResponse(code = 200, message = "Status of current ff4j security bean", response = AuthorizationsManagerApiBean::class),
                ApiResponse(code = 404, message = "no security has been defined"))
        @GetMapping(value = [(ROOT + RESOURCE_SECURITY)], produces = [APPLICATION_JSON_VALUE])
        get() = ff4JServices.getSecurityInfo()


    @ApiOperation(value = "<b>Simple check</b> feature toggle", response = Boolean::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "If feature is flipped"),
            ApiResponse(code = 404, message = "Feature not found"))
    @GetMapping(value = [(ROOT + OPERATION_CHECK + ROOT + PATH_PARAM_UID)])
    fun check(@PathVariable(value = PARAM_UID) featureUID: String): ResponseEntity<Boolean> {
        val status = ff4JServices.check(featureUID)
        return ResponseEntity(status, OK)
    }

    @ApiOperation(value = "<b>Advanced check</b> feature toggle (parametrized)", response = Boolean::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "If feature is flipped"),
            ApiResponse(code = 400, message = "Invalid parameter"),
            ApiResponse(code = 404, message = "Feature not found"))
    @PostMapping(value = [(ROOT + OPERATION_CHECK + ROOT + PATH_PARAM_UID)], consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun check(@PathVariable(value = PARAM_UID) featureUID: String, @RequestParam formParams: MultiValueMap<String, String>): ResponseEntity<Boolean> {
        val map = formParams.toSingleValueMap()
        val status = ff4JServices.check(featureUID, map)
        return ResponseEntity(status, OK)
    }
}
