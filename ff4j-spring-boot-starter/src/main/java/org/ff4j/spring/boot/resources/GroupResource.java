package org.ff4j.spring.boot.resources;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.ff4j.spring.boot.constants.CommonConstants;
import org.ff4j.spring.boot.constants.FeatureConstants;
import org.ff4j.spring.boot.domain.FeatureApiBean;
import org.ff4j.spring.boot.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.ff4j.web.FF4jWebConstants.OPERATION_DISABLE;
import static org.ff4j.web.FF4jWebConstants.OPERATION_ENABLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RestController
@RequestMapping(value = FeatureConstants.RESOURCE_FF4J_STORE_GROUPS + CommonConstants.ROOT + FeatureConstants.PATH_PARAM_GROUP)
public class GroupResource {

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all the features belonging to the group", response = FeatureApiBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "features belonging to the group"),
            @ApiResponse(code = 404, message = "Group not found")})
    public Collection<FeatureApiBean> getFeaturesByGroup(@PathVariable(value = FeatureConstants.PARAM_GROUP) String groupName) {
        return groupService.getFeaturesByGroup(groupName);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_ENABLE, method = POST, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enable a group", response = Void.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Group has been enabled"),
            @ApiResponse(code = 404, message = "Group not found")})
    public void enableGroup(@PathVariable(value = FeatureConstants.PARAM_GROUP) String groupName) {
        groupService.enableGroup(groupName);
    }

    @RequestMapping(value = CommonConstants.ROOT + OPERATION_DISABLE, method = POST, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Disable a group", response = Void.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Group has been disabled"),
            @ApiResponse(code = 404, message = "Group not found")})
    public void disableGroup(@PathVariable(value = FeatureConstants.PARAM_GROUP) String groupName) {
        groupService.disableGroup(groupName);
    }
}
