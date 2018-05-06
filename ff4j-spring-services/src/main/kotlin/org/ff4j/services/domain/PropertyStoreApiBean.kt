package org.ff4j.services.domain

import org.ff4j.cache.FF4jCacheProxy
import org.ff4j.property.store.PropertyStore
import java.io.Serializable
import java.util.*

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class PropertyStoreApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 5459281574635411541L
    }

    var type: String? = null
    var numberOfProperties: Int = 0
    var properties: Set<String> = HashSet()
    var cache: CacheApiBean? = null

    constructor() : super()

    constructor(propertyStore: PropertyStore) {
        this.type = propertyStore.javaClass.canonicalName
        if (propertyStore is FF4jCacheProxy) {
            this.cache = CacheApiBean(propertyStore as PropertyStore)
        }
        this.properties = propertyStore.listPropertyNames()
        this.numberOfProperties = properties.size
    }
}