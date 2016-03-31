package org.ff4j.spring.boot.services;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.spring.boot.domain.FeatureApiBean;
import org.ff4j.spring.boot.validator.FeatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
public class GroupService {
    @Autowired
    private FF4j ff4j;
    @Autowired
    private FeatureValidator featureValidator;

    public Collection<FeatureApiBean> getFeaturesByGroup(String groupName) {
        featureValidator.assertGroupExist(groupName);
        Collection<Feature> features = ff4j.getFeatureStore().readGroup(groupName).values();
        Collection<FeatureApiBean> featureApiBeans = new ArrayList<>();
        if (!CollectionUtils.isEmpty(features)) {
            featureApiBeans.addAll(features.stream().map(FeatureApiBean::new).collect(Collectors.toList()));
        }
        return featureApiBeans;
    }

    public void enableGroup(String groupName) {
        featureValidator.assertGroupExist(groupName);
        ff4j.getFeatureStore().enableGroup(groupName);
    }

    public void disableGroup(String groupName) {
        featureValidator.assertGroupExist(groupName);
        ff4j.getFeatureStore().disableGroup(groupName);
    }
}
