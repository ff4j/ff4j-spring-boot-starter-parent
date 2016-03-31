package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.cache.FF4jCacheProxy;
import org.ff4j.property.store.PropertyStore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class PropertyStoreApiBean implements Serializable {

    private static final long serialVersionUID = 5459281574635411541L;

    @Getter
    private String type;

    @Getter
    private int numberOfProperties;

    @Getter
    private Set<String> properties = new HashSet<>();

    @Getter
    private CacheApiBean cache;

    public PropertyStoreApiBean(PropertyStore pStore) {
        type = pStore.getClass().getCanonicalName();
        if (pStore instanceof FF4jCacheProxy) {
            cache = new CacheApiBean(pStore);
        }
        properties = pStore.listPropertyNames();
        numberOfProperties = properties.size();
    }
}
