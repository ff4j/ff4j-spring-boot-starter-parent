package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.cache.FF4jCacheProxy;
import org.ff4j.core.FeatureStore;
import org.ff4j.property.store.PropertyStore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class CacheApiBean implements Serializable {

    private static final long serialVersionUID = -2564221971597313125L;

    @Getter
    private String cacheProvider;

    @Getter
    private String cacheStore;

    @Getter
    private Set<String> featureNames = new HashSet<>();

    @Getter
    private Set<String> propertyNames = new HashSet<>();

    public CacheApiBean(FeatureStore featureStore) {
        if (featureStore instanceof FF4jCacheProxy) {
            FF4jCacheProxy cacheProxy = (FF4jCacheProxy) featureStore;
            cacheStore = cacheProxy.getCachedTargetStore();
            cacheProvider = cacheProxy.getCacheProvider();
            featureNames = cacheProxy.getCacheManager().listCachedFeatureNames();
        }
    }

    public CacheApiBean(PropertyStore propertyStore) {
        if (propertyStore instanceof FF4jCacheProxy) {
            FF4jCacheProxy cacheProxy = (FF4jCacheProxy) propertyStore;
            // FIXME : This is wrong. Need to ask Cedrick to change the implementation
            cacheStore = cacheProxy.getCachedTargetStore();
            cacheProvider = cacheProxy.getCacheProvider();
            propertyNames = cacheProxy.getCacheManager().listCachedPropertyNames();
        }
    }
}
