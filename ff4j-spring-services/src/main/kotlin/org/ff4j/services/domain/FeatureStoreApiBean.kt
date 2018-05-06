package org.ff4j.services.domain

import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.core.FeatureStore
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FeatureStoreApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 1868920596870427435L
    }

    var type: String? = null
    var numberOfFeatures: Int = 0
    var numberOfGroups: Int = 0
    var features: MutableList<String> = ArrayList()
    var groups: MutableList<String> = ArrayList()
    var cache: CacheApiBean? = null

    constructor() : super()

    constructor(featureStore: FeatureStore) {
        type = featureStore.javaClass.canonicalName
        if (featureStore is FF4jCacheProxy) {
            cache = CacheApiBean(featureStore as FeatureStore)
        }
        features = ArrayList(featureStore.readAll().keys)
        groups = ArrayList(featureStore.readAllGroups())
        numberOfFeatures = features.size
        numberOfGroups = groups.size
    }
}