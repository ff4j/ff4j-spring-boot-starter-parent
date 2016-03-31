package org.ff4j.spring.boot.validator;

import org.apache.commons.lang3.StringUtils;
import org.ff4j.FF4j;
import org.ff4j.spring.boot.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Component
public class FeatureValidator {
    @Autowired
    private FF4j ff4j;

    public void assertFeatureIdsMatch(String featureUID, String featureApiUID) {
        if (!featureUID.equals(featureApiUID)) {
            throw new FeatureIdNotMatchException();
        }
    }

    public void assertFeatureUIDIsNotBlank(String featureUID) {
        if (StringUtils.isBlank(featureUID)) {
            throw new FeatureIdBlankException();
        }
    }

    public void assertFeatureExists(String featureUID) {
        if (!ff4j.exist(featureUID)) {
            throw new FeatureNotFoundException();
        }
    }

    public void assertRoleDoesNotExist(String featureUID, String role) {
        if (ff4j.getFeatureStore().read(featureUID).getPermissions().contains(role)) {
            throw new RoleExistsException();
        }
    }

    public void assertRoleExist(String featureUID, String role) {
        if (!ff4j.getFeatureStore().read(featureUID).getPermissions().contains(role)) {
            throw new RoleNotExistsException();
        }
    }

    public void assertGroupDoesNotExist(String groupName) {
        if (ff4j.getFeatureStore().existGroup(groupName)) {
            throw new GroupExistsException();
        }
    }

    public void assertGroupExist(String groupName) {
        if (!ff4j.getFeatureStore().existGroup(groupName)) {
            throw new GroupNotExistsException();
        }
    }
}
