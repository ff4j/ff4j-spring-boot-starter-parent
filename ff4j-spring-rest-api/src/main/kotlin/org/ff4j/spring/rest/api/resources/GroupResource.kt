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
import org.ff4j.services.GroupServices
import org.ff4j.services.constants.FeatureConstants.PARAM_GROUP
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_GROUP
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J
import org.ff4j.services.constants.FeatureConstants.RESOURCE_GROUPS
import org.ff4j.services.constants.FeatureConstants.RESOURCE_STORE
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.web.FF4jWebConstants.OPERATION_DISABLE
import org.ff4j.web.FF4jWebConstants.OPERATION_ENABLE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Tag(name = "Groups", description = "The API for group related operations")
@RestController
@RequestMapping(value = ["\${ff4j.api.context-path:$RESOURCE_FF4J}$RESOURCE_STORE$RESOURCE_GROUPS/$PATH_PARAM_GROUP"])
class GroupResource(@Autowired val groupServices: GroupServices) {

  @Operation(summary = "Get all the features belonging to the group", tags = ["Groups"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200",
      description = "features belonging to the group",
      content = arrayOf(Content(schema = Schema(implementation = FeatureApiBean::class)))
    ), ApiResponse(responseCode = "404", description = "Group not found")]
  )
  @GetMapping(produces = [APPLICATION_JSON_VALUE])
  fun getFeaturesByGroup(@PathVariable(value = PARAM_GROUP) groupName: String): ResponseEntity<Flux<FeatureApiBean>> =
    ResponseEntity.ok(groupServices.getFeaturesByGroup(groupName))

  @Operation(summary = "Enable a group", tags = ["Groups"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200", description = "Group has been enabled"
    ), ApiResponse(responseCode = "404", description = "Group not found")]
  )
  @PostMapping(value = [("/$OPERATION_ENABLE")], produces = [APPLICATION_JSON_VALUE])
  fun enableGroup(@PathVariable(value = PARAM_GROUP) groupName: String): ResponseEntity<Unit> =
    ResponseEntity.ok(groupServices.enableGroup(groupName))

  @Operation(summary = "Disable a group", tags = ["Groups"])
  @ApiResponses(
    value = [ApiResponse(
      responseCode = "200", description = "Group has been disabled"
    ), ApiResponse(responseCode = "404", description = "Group not found")]
  )
  @PostMapping(value = [("/$OPERATION_DISABLE")], produces = [APPLICATION_JSON_VALUE])
  fun disableGroup(@PathVariable(value = PARAM_GROUP) groupName: String): ResponseEntity<Unit> =
    ResponseEntity.ok(groupServices.disableGroup(groupName))
}
