package org.ff4j.spring.boot.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public enum FeatureActions {
    CREATED, UPDATED;


    public static ResponseEntity<Boolean> getBooleanResponseEntityByHttpStatus(FeatureActions featureActions) {
        switch (featureActions) {
            case CREATED:
                return new ResponseEntity<>(TRUE, HttpStatus.CREATED);
            case UPDATED:
                return new ResponseEntity<>(TRUE, HttpStatus.ACCEPTED);
            default:
                return new ResponseEntity<>(FALSE, HttpStatus.NO_CONTENT);
        }
    }
}
