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
