package org.ff4j.services

import org.ff4j.FF4j
import org.ff4j.services.domain.PropertyApiBean
import org.ff4j.services.model.FeatureActions
import org.ff4j.services.model.FeatureActions.CREATED
import org.ff4j.services.model.FeatureActions.UPDATED
import org.ff4j.services.validator.PropertyValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class PropertyServices(@Autowired val fF4j: FF4j, @Autowired val propertyValidator: PropertyValidator) {
    fun getProperty(propertyUID: String): PropertyApiBean {
        propertyValidator.assertPropertyExist(propertyUID)
        return PropertyApiBean(fF4j.propertiesStore.readProperty(propertyUID))
    }

    fun createOrUpdateProperty(propertyUID: String, propertyApiBean: PropertyApiBean): FeatureActions {
        propertyValidator.assertPropertyNameNotBlank(propertyApiBean.name)
        propertyValidator.assertPropertyNameMatch(propertyUID, propertyApiBean.name)
        val property = propertyApiBean.asProperty()
        return if (fF4j.propertiesStore.existProperty(propertyUID)) {
            fF4j.propertiesStore.updateProperty(property)
            UPDATED
        } else {
            fF4j.propertiesStore.createProperty(property)
            CREATED
        }
    }

    fun deleteProperty(propertyUID: String) {
        propertyValidator.assertPropertyExist(propertyUID)
        fF4j.propertiesStore.deleteProperty(propertyUID)
    }

    fun updatePropertyName(propertyUID: String, newPropertyName: String) {
        propertyValidator.assertPropertyExist(propertyUID)
        fF4j.propertiesStore.updateProperty(propertyUID, newPropertyName)
    }
}