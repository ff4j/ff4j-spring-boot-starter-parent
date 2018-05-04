package org.ff4j.services.domain

import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.core.FeatureStore
import org.ff4j.property.store.PropertyStore
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class CacheApiBean : Serializable {

    companion object {
        private const val serialVersionUID = -2564221971597313125L
    }

    var cacheProvider: String? = null
    var cacheStore: String? = null
    var featureNames: MutableSet<String> = HashSet()
    var propertyNames: MutableSet<String> = HashSet()

    constructor() : super()

    constructor(featureStore: FeatureStore) {
        if (featureStore is FF4jCacheProxy) {
            this.cacheStore = featureStore.cachedTargetStore
            this.cacheProvider = featureStore.cacheProvider
            this.featureNames = featureStore.cacheManager.listCachedFeatureNames()
        }
    }

    constructor(propertyStore: PropertyStore) {
        if (propertyStore is FF4jCacheProxy) {
            this.cacheStore = propertyStore.cachedTargetStore
            this.cacheProvider = propertyStore.cacheProvider
            this.propertyNames = propertyStore.cacheManager.listCachedPropertyNames()
        }
    }
}