package org.ff4j.services.domain

import org.ff4j.core.Feature
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FeatureApiBean : Serializable {

    companion object {
        private const val serialVersionUID = -4977143873952901044L
    }

    var uid: String? = null
    var description: String? = null
    var group: String? = null
    var flippingStrategy: FlippingStrategyApiBean? = null
    var permissions: MutableList<String> = ArrayList()
    var customProperties: MutableMap<String, PropertyApiBean> = HashMap()
    var enable: Boolean = false

    constructor() : super()

    constructor(feature: Feature) {
        this.uid = feature.uid
        this.enable = feature.isEnable
        this.description = feature.description
        this.permissions = ArrayList(feature.permissions)
        feature.flippingStrategy?.let {
            this.flippingStrategy = FlippingStrategyApiBean(it)
        }
        feature.customProperties?.let {
            it.values.forEach { property ->
                run {
                    this.customProperties.put(property.name, PropertyApiBean(property))
                }
            }
        }
    }
}