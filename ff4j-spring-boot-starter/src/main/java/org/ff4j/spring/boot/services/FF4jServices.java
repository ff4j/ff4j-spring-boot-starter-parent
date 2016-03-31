package org.ff4j.spring.boot.services;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.security.AuthorizationsManager;
import org.ff4j.spring.boot.domain.AuthorizationsManagerApiBean;
import org.ff4j.spring.boot.domain.FF4jStatusApiBean;
import org.ff4j.spring.boot.exceptions.AuthorizationNotExistsException;
import org.ff4j.spring.boot.validator.FeatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FF4jServices {
    @Autowired
    private FF4j ff4j;
    @Autowired
    private FeatureValidator featureValidator;

    public FF4jStatusApiBean getStatus() {
        return new FF4jStatusApiBean(ff4j);
    }

    public AuthorizationsManagerApiBean getSecurityInfo() {
        AuthorizationsManager authorizationsManager = ff4j.getAuthorizationsManager();
        if (null == authorizationsManager) {
            throw new AuthorizationNotExistsException();
        }
        return new AuthorizationsManagerApiBean(authorizationsManager);
    }

    public Boolean check(String featureUID) {
        featureValidator.assertFeatureExists(featureUID);
        return ff4j.check(featureUID);
    }

    public Boolean check(String featureUID, Map<String, String> map) {
        featureValidator.assertFeatureExists(featureUID);
        FlippingExecutionContext flipExecCtx = new FlippingExecutionContext();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            flipExecCtx.putString(entry.getKey(), entry.getValue());
        }
        return ff4j.check(featureUID, flipExecCtx);
    }
}
