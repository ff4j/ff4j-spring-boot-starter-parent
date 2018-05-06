package org.ff4j.services

import org.ff4j.FF4j
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.model.FeatureActions
import org.ff4j.services.validator.FeatureValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class FeatureServices(@Autowired val fF4j: FF4j, @Autowired val featureValidator: FeatureValidator) {
    fun getFeature(featureUID: String): FeatureApiBean {
        featureValidator.assertFeatureExists(featureUID)
        return FeatureApiBean(fF4j.featureStore.read(featureUID))
    }

    fun createOrUpdateFeature(featureUID: String, featureApiBean: FeatureApiBean): FeatureActions {
        featureValidator.assertFeatureUIDIsNotBlank(featureApiBean.uid)
        featureValidator.assertFeatureIdsMatch(featureUID, featureApiBean.uid)
        return if (fF4j.featureStore.exist(featureUID)) {
            fF4j.featureStore.update(featureApiBean.toFeature())
            FeatureActions.UPDATED
        } else {
            fF4j.featureStore.create(featureApiBean.toFeature())
            FeatureActions.CREATED
        }
    }

    fun deleteFeature(featureUID: String) {
        featureValidator.assertFeatureExists(featureUID)
        fF4j.featureStore.delete(featureUID)
    }

    fun disableFeature(featureUID: String) {
        featureValidator.assertFeatureExists(featureUID)
        fF4j.featureStore.disable(featureUID)
    }

    fun enableFeature(featureUID: String) {
        featureValidator.assertFeatureExists(featureUID)
        fF4j.featureStore.enable(featureUID)
    }

    fun grantRoleToFeature(featureUID: String, roleName: String) {
        featureValidator.assertFeatureExists(featureUID)
        featureValidator.assertRoleDoesNotExist(featureUID, roleName)
        fF4j.featureStore.grantRoleOnFeature(featureUID, roleName)
    }

    fun removeRoleFromFeature(featureUID: String, roleName: String) {
        featureValidator.assertFeatureExists(featureUID)
        featureValidator.assertRoleExist(featureUID, roleName)
        fF4j.featureStore.removeRoleFromFeature(featureUID, roleName)
    }

    fun addGroupToFeature(featureUID: String, groupName: String) {
        featureValidator.assertFeatureExists(featureUID)
        featureValidator.assertGroupDoesNotExist(groupName)
        fF4j.featureStore.addToGroup(featureUID, groupName)
    }

    fun removeGroupFromFeature(featureUID: String, groupName: String) {
        featureValidator.assertFeatureExists(featureUID)
        featureValidator.assertGroupExist(groupName)
        fF4j.featureStore.removeFromGroup(featureUID, groupName)
    }
}
