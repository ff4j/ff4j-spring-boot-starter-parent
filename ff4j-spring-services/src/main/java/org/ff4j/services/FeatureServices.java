package org.ff4j.services;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.exception.FeatureAccessException;
import org.ff4j.services.domain.FeatureApiBean;
import org.ff4j.services.domain.FlippingStrategyApiBean;
import org.ff4j.services.domain.PropertyApiBean;
import org.ff4j.services.exceptions.FlippingStrategyBadRequestException;
import org.ff4j.services.exceptions.PropertiesBadRequestException;
import org.ff4j.services.model.FeatureActions;
import org.ff4j.services.validator.FeatureValidator;
import org.ff4j.utils.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
public class FeatureServices {
    @Autowired
    private FF4j ff4j;
    @Autowired
    private FeatureValidator featureValidator;

    public FeatureApiBean getFeature(String featureUID) {
        featureValidator.assertFeatureExists(featureUID);
        return new FeatureApiBean(ff4j.getFeatureStore().read(featureUID));
    }

    public FeatureActions createOrUpdateFeature(String featureUID, FeatureApiBean featureApiBean) {
        featureValidator.assertFeatureUIDIsNotBlank(featureApiBean.getUid());
        featureValidator.assertFeatureIdsMatch(featureUID, featureApiBean.getUid());
        Feature feature = new Feature(featureUID);
        feature.setDescription(featureApiBean.getDescription());
        feature.setEnable(featureApiBean.getEnable());
        feature.setGroup(featureApiBean.getGroup());
        feature.setPermissions(new HashSet<>(featureApiBean.getPermissions()));
        initFlippingStrategy(featureApiBean, feature);
        initProperties(featureApiBean, feature);
        if (ff4j.getFeatureStore().exist(featureUID)) {
            ff4j.getFeatureStore().update(feature);
            return FeatureActions.UPDATED;
        } else {
            ff4j.getFeatureStore().create(feature);
            return FeatureActions.CREATED;
        }
    }

    private void initProperties(FeatureApiBean featureApiBean, Feature feature) {
        Map<String, PropertyApiBean> mapProperties = featureApiBean.getCustomProperties();
        if (mapProperties != null) {
            try {
                for (PropertyApiBean propertyBean : mapProperties.values()) {
                    feature.addProperty(propertyBean.asProperty());
                }
            } catch (IllegalArgumentException exception) {
                throw new PropertiesBadRequestException(exception);
            }
        }
    }

    private void initFlippingStrategy(FeatureApiBean featureApiBean, Feature feature) {
        FlippingStrategyApiBean flipApiBean = featureApiBean.getFlippingStrategy();
        if (null != flipApiBean) {
            try {
                Map<String, String> initParams = flipApiBean.getInitParams();
                feature.setFlippingStrategy(MappingUtil.instanceFlippingStrategy(featureApiBean.getUid(), flipApiBean.getType(), initParams));
            } catch (FeatureAccessException exception) {
                throw new FlippingStrategyBadRequestException(exception);
            }
        }
    }

    public void deleteFeature(String featureUID) {
        featureValidator.assertFeatureExists(featureUID);
        ff4j.getFeatureStore().delete(featureUID);
    }

    public void enableFeature(String featureUID) {
        featureValidator.assertFeatureExists(featureUID);
        ff4j.getFeatureStore().enable(featureUID);
    }

    public void disableFeature(String featureUID) {
        featureValidator.assertFeatureExists(featureUID);
        ff4j.getFeatureStore().disable(featureUID);
    }

    public void grantRoleToFeature(String featureUID, String role) {
        featureValidator.assertFeatureExists(featureUID);
        featureValidator.assertRoleDoesNotExist(featureUID, role);
        ff4j.getFeatureStore().grantRoleOnFeature(featureUID, role);
    }

    public void removeRoleFromFeature(String featureUID, String role) {
        featureValidator.assertFeatureExists(featureUID);
        featureValidator.assertRoleExist(featureUID, role);
        ff4j.getFeatureStore().removeRoleFromFeature(featureUID, role);
    }

    public void addGroupToFeature(String featureUID, String groupName) {
        featureValidator.assertFeatureExists(featureUID);
        featureValidator.assertGroupDoesNotExist(groupName);
        ff4j.getFeatureStore().addToGroup(featureUID, groupName);
    }

    public void removeGroupFromFeature(String featureUID, String groupName) {
        featureValidator.assertFeatureExists(featureUID);
        featureValidator.assertGroupExist(groupName);
        ff4j.getFeatureStore().removeFromGroup(featureUID, groupName);
    }
}
