package org.ff4j.services.validator

import org.apache.commons.lang3.StringUtils
import org.ff4j.FF4j
import org.ff4j.services.exceptions.PropertyNameBlankException
import org.ff4j.services.exceptions.PropertyNameNotMatchException
import org.ff4j.services.exceptions.PropertyNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PropertyValidator {
    @Autowired
    lateinit var ff4j: FF4j

    fun assertPropertyExist(propertyName: String) {
        if (!ff4j.propertiesStore.existProperty(propertyName)) {
            throw PropertyNotFoundException()
        }
    }

    fun assertPropertyNameNotBlank(name: String) {
        if (StringUtils.isBlank(name)) {
            throw PropertyNameBlankException()
        }
    }

    fun assertPropertyNameMatch(propertyName: String, propertyApiBeanName: String) {
        if (!propertyName.equals(propertyApiBeanName, ignoreCase = true)) {
            throw PropertyNameNotMatchException()
        }
    }
}