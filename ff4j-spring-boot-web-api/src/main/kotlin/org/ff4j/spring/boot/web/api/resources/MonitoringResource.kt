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
import org.ff4j.services.MonitoringServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J_MONITORING
import org.ff4j.services.domain.EventRepositoryApiBean
import org.ff4j.web.FF4jWebConstants.PARAM_END
import org.ff4j.web.FF4jWebConstants.PARAM_START
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Api(tags = ["Monitoring"], description = "The API for monitoring related operations")
@RestController
@RequestMapping(value = [RESOURCE_FF4J_MONITORING])
class MonitoringResource(@Autowired val monitoringServices: MonitoringServices) {

    @ApiOperation(value = "Display Monitoring information for all features", notes = "The EventRepository handle to store audit events is not required", response = EventRepositoryApiBean::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Status of event repository bean", response = EventRepositoryApiBean::class),
            ApiResponse(code = 404, message = "No event repository defined", response = String::class))
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getMonitoringStatus(@RequestParam(value = PARAM_START, required = false, defaultValue = "0") start: Long, @RequestParam(value = PARAM_END, required = false, defaultValue = "0") end: Long): EventRepositoryApiBean =
            monitoringServices.getMonitoringStatus(start, end)
}
