package org.ff4j.spring.boot.services;

import org.ff4j.FF4j;
import org.ff4j.property.Property;
import org.ff4j.spring.boot.domain.PropertyApiBean;
import org.ff4j.spring.boot.model.FeatureActions;
import org.ff4j.spring.boot.validator.PropertyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
public class PropertyService {
    @Autowired
    private FF4j ff4j;
    @Autowired
    private PropertyValidator propertyValidator;

    public PropertyApiBean getProperty(String propertyName) {
        propertyValidator.assertPropertyExist(propertyName);
        return new PropertyApiBean(ff4j.getPropertiesStore().readProperty(propertyName));
    }

    public FeatureActions createOrUpdateProperty(String propertyName, PropertyApiBean propertyApiBean) {
        propertyValidator.assertPropertyNameNotBlank(propertyApiBean.getName());
        propertyValidator.assertPropertyNameMatch(propertyName, propertyApiBean.getName());
        Property<?> property = propertyApiBean.asProperty();
        if (ff4j.getPropertiesStore().existProperty(propertyName)) {
            ff4j.getPropertiesStore().updateProperty(property);
            return FeatureActions.UPDATED;
        } else {
            ff4j.getPropertiesStore().createProperty(property);
            return FeatureActions.CREATED;
        }
    }

    public void deleteProperty(String propertyName) {
        propertyValidator.assertPropertyExist(propertyName);
        ff4j.getPropertiesStore().deleteProperty(propertyName);
    }

    public void updatePropertyName(String propertyName, String newValue) {
        propertyValidator.assertPropertyExist(propertyName);
        ff4j.getPropertiesStore().updateProperty(propertyName, newValue);
    }
}
