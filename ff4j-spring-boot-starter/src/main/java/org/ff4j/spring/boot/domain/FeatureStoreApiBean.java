package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.cache.FF4jCacheProxy;
import org.ff4j.core.FeatureStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class FeatureStoreApiBean implements Serializable {

    private static final long serialVersionUID = 1868920596870427435L;

    @Getter
    private String type;

    @Getter
    private int numberOfFeatures;

    @Getter
    private int numberOfGroups;

    @Getter
    private List<String> features = new ArrayList<>();

    @Getter
    private List<String> groups = new ArrayList<>();

    @Getter
    private CacheApiBean cache;

    public FeatureStoreApiBean(FeatureStore featureStore) {
        type = featureStore.getClass().getCanonicalName();
        if (featureStore instanceof FF4jCacheProxy) {
            cache = new CacheApiBean(featureStore);
        }
        features = new ArrayList<>(featureStore.readAll().keySet());
        groups = new ArrayList<>(featureStore.readAllGroups());
        numberOfFeatures = features.size();
        numberOfGroups = groups.size();
    }
}
