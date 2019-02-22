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
import org.ff4j.services.GroupServices
import org.ff4j.services.constants.FeatureConstants.PARAM_GROUP
import org.ff4j.services.constants.FeatureConstants.PATH_PARAM_GROUP
import org.ff4j.services.constants.FeatureConstants.RESOURCE_FF4J_STORE_GROUPS
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.web.FF4jWebConstants.OPERATION_DISABLE
import org.ff4j.web.FF4jWebConstants.OPERATION_ENABLE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Api(tags = ["Group"], description = "The API for group related operations")
@RestController
@RequestMapping(value = [("$RESOURCE_FF4J_STORE_GROUPS/$PATH_PARAM_GROUP")])
class GroupResource(@Autowired val groupServices: GroupServices) {

    @ApiOperation(value = "Get all the features belonging to the group", response = FeatureApiBean::class)
    @ApiResponses(ApiResponse(code = 200, message = "features belonging to the group"), ApiResponse(code = 404, message = "Group not found"))
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getFeaturesByGroup(@PathVariable(value = PARAM_GROUP) groupName: String): Collection<FeatureApiBean> = groupServices.getFeaturesByGroup(groupName)

    @ApiOperation(value = "Enable a group", response = Void::class)
    @ApiResponses(ApiResponse(code = 200, message = "Group has been enabled"), ApiResponse(code = 404, message = "Group not found"))
    @PostMapping(value = [("/$OPERATION_ENABLE")], produces = [APPLICATION_JSON_VALUE])
    fun enableGroup(@PathVariable(value = PARAM_GROUP) groupName: String) = groupServices.enableGroup(groupName)

    @ApiOperation(value = "Disable a group", response = Void::class)
    @ApiResponses(ApiResponse(code = 200, message = "Group has been disabled"), ApiResponse(code = 404, message = "Group not found"))
    @PostMapping(value = [("/$OPERATION_DISABLE")], produces = [APPLICATION_JSON_VALUE])
    fun disableGroup(@PathVariable(value = PARAM_GROUP) groupName: String) = groupServices.disableGroup(groupName)
}
