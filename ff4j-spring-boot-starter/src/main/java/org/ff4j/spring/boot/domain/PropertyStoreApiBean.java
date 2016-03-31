/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */

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
