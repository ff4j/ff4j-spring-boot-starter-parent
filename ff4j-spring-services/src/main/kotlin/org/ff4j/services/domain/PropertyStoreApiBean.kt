/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2019 FF4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
        if (isInstanceOfCache(propertyStore)) {
            this.cache = CacheApiBean(propertyStore)
        }
        this.properties = propertyStore.listPropertyNames()
        this.numberOfProperties = properties.size
    }

    private fun isInstanceOfCache(propertyStore: PropertyStore) = propertyStore is FF4jCacheProxy
}
