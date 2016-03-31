package org.ff4j.spring.boot.services;


import org.apache.commons.lang3.StringUtils;
import org.ff4j.FF4j;
import org.ff4j.cache.FF4jCacheProxy;
import org.ff4j.core.Feature;
import org.ff4j.spring.boot.domain.CacheApiBean;
import org.ff4j.spring.boot.domain.FeatureApiBean;
import org.ff4j.spring.boot.domain.FeatureStoreApiBean;
import org.ff4j.spring.boot.exceptions.FeatureStoreNotCached;
import org.ff4j.spring.boot.domain.GroupDescApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
public class FeatureStoreServices {
    @Autowired
    private FF4j ff4j;

    public FeatureStoreApiBean getFeatureStore() {
        return new FeatureStoreApiBean(ff4j.getFeatureStore());
    }

    public Collection<FeatureApiBean> getAllFeatures() {
        List<FeatureApiBean> features;
        Map<String, Feature> featureMap = ff4j.getFeatureStore().readAll();
        if (CollectionUtils.isEmpty(featureMap)) {
            features = new ArrayList<>(0);
        } else {
            features = new ArrayList<>(featureMap.size());
            features.addAll(featureMap.values().stream().map(FeatureApiBean::new).collect(Collectors.toList()));
        }
        return features;
    }

    public Collection<GroupDescApiBean> getAllGroups() {
        Map<String, GroupDescApiBean> groups = new HashMap<>();
        Map<String, Feature> featureMap = ff4j.getFeatureStore().readAll();
        if (!CollectionUtils.isEmpty(featureMap)) {
            for (Feature feature : featureMap.values()) {
                initGroupMap(groups, feature.getUid(), feature.getGroup());
            }
        }
        return groups.values();
    }

    private void initGroupMap(Map<String, GroupDescApiBean> groups, String featureUID, String groupName) {
        if (StringUtils.isNotBlank(groupName)) {
            if (groups.containsKey(groupName)) {
                groups.get(groupName).getFeatures().add(featureUID);
            } else {
                groups.put(groupName, new GroupDescApiBean(groupName, new ArrayList<>()));
                groups.get(groupName).getFeatures().add(featureUID);
            }
        }
    }

    public void deleteAllFeatures() {
        ff4j.getFeatureStore().clear();
    }

    public CacheApiBean getFeaturesFromCache() {
        if (ff4j.getFeatureStore() instanceof FF4jCacheProxy) {
            return new CacheApiBean(ff4j.getFeatureStore());
        } else {
            throw new FeatureStoreNotCached();
        }
    }

    public void clearCachedFeatureStore() {
        if (ff4j.getFeatureStore() instanceof FF4jCacheProxy) {
            ((FF4jCacheProxy) ff4j.getFeatureStore()).getCacheManager().clearFeatures();
        } else {
            throw new FeatureStoreNotCached();
        }
    }
}
