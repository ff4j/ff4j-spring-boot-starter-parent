package org.ff4j.services.validator

import org.apache.commons.lang3.StringUtils
import org.ff4j.FF4j
import org.ff4j.services.exceptions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FeatureValidator {
    @Autowired
    lateinit var ff4j: FF4j

    fun assertFeatureIdsMatch(featureUID: String, featureApiUID: String) {
        if (featureUID != featureApiUID) {
            throw FeatureIdNotMatchException()
        }
    }

    fun assertFeatureUIDIsNotBlank(featureUID: String) {
        if (StringUtils.isBlank(featureUID)) {
            throw FeatureIdBlankException()
        }
    }

    fun assertFeatureExists(featureUID: String) {
        if (!ff4j.exist(featureUID)) {
            throw FeatureNotFoundException()
        }
    }

    fun assertRoleDoesNotExist(featureUID: String, role: String) {
        if (ff4j.featureStore.read(featureUID).permissions.contains(role)) {
            throw RoleExistsException()
        }
    }

    fun assertRoleExist(featureUID: String, role: String) {
        if (!ff4j.featureStore.read(featureUID).permissions.contains(role)) {
            throw RoleNotExistsException()
        }
    }

    fun assertGroupDoesNotExist(groupName: String) {
        if (ff4j.featureStore.existGroup(groupName)) {
            throw GroupExistsException()
        }
    }

    fun assertGroupExist(groupName: String) {
        if (!ff4j.featureStore.existGroup(groupName)) {
            throw GroupNotExistsException()
        }
    }
}