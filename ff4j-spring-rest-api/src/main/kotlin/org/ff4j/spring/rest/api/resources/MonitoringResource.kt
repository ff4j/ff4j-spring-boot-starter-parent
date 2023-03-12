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
import org.ff4j.services.MonitoringServices
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.RESOURCE_MONITORING
import org.ff4j.services.domain.EventRepositoryApiBean
import org.ff4j.web.FF4jWebConstants.PARAM_END
import org.ff4j.web.FF4jWebConstants.PARAM_START
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "Monitoring", description = "The API for monitoring related operations")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}$RESOURCE_MONITORING"])
class MonitoringResource(@Autowired val monitoringServices: MonitoringServices) {

  @Operation(
    summary = "Display Monitoring information for all features",
    description = "The EventRepository handle to store audit events is not required",
    tags = ["Monitoring"]
  )
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "Status of event repository bean",
      content = arrayOf(Content(schema = Schema(implementation = EventRepositoryApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "No event repository defined")]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getMonitoringStatus(
    @RequestParam(
      value = PARAM_START, required = false, defaultValue = "0"
    ) start: Long, @RequestParam(value = PARAM_END, required = false, defaultValue = "0") end: Long
  ): ResponseEntity<Mono<EventRepositoryApiBean>> =
    ResponseEntity.ok(monitoringServices.getMonitoringStatus(start, end))
}
