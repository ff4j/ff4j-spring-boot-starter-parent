package org.ff4j.spring.boot.validator;

import org.apache.commons.lang3.StringUtils;
import org.ff4j.FF4j;
import org.ff4j.spring.boot.exceptions.PropertyNameNotMatchException;
import org.ff4j.spring.boot.exceptions.PropertyNotFoundException;
import org.ff4j.spring.boot.exceptions.PropertyNameBlankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Component
public class PropertyValidator {
    @Autowired
    private FF4j ff4j;


    public void assertPropertyExist(String propertyName) {
        if (!ff4j.getPropertiesStore().existProperty(propertyName)) {
            throw new PropertyNotFoundException();
        }
    }

    public void assertPropertyNameNotBlank(String name) {
        if (StringUtils.isBlank(name)) {
            throw new PropertyNameBlankException();
        }
    }

    public void assertPropertyNameMatch(String propertyName, String propertyApiBeanName) {
        if (!propertyName.equalsIgnoreCase(propertyApiBeanName)) {
            throw new PropertyNameNotMatchException();
        }
    }
}
