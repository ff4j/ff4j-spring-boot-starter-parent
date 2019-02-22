package org.ff4j.services.domain

import org.ff4j.core.Feature
import org.ff4j.exception.FeatureAccessException
import org.ff4j.services.exceptions.FlippingStrategyBadRequestException
import org.ff4j.services.exceptions.PropertiesBadRequestException
import org.ff4j.utils.MappingUtil
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
    var permissions: MutableSet<String> = HashSet()
    var customProperties: MutableMap<String, PropertyApiBean> = HashMap()
    var enable: Boolean = false

    constructor() : super()

    constructor(feature: Feature) {
        this.uid = feature.uid
        this.enable = feature.isEnable
        this.description = feature.description
        this.permissions = feature.permissions
        this.group = feature.group
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

    fun toFeature(): Feature {
        val feature = Feature(uid)
        feature.description = description
        feature.isEnable = enable
        feature.group = group
        feature.permissions = permissions
        initFlippingStrategy(feature)
        initProperties(feature)
        return feature
    }

    private fun initFlippingStrategy(feature: Feature) {
        flippingStrategy?.let {
            try {
                feature.flippingStrategy = MappingUtil.instanceFlippingStrategy(uid, it.type, it.initParams)
            } catch (exception: FeatureAccessException) {
                throw FlippingStrategyBadRequestException(exception)
            }
        }
    }

    private fun initProperties(feature: Feature) {
        addProperty(customProperties.values, feature)
    }

    private fun addProperty(values: MutableCollection<PropertyApiBean>, feature: Feature) {
        values.forEach { propertyApiBean ->
            run {
                try {
                    feature.addProperty(propertyApiBean.asProperty())
                } catch (exception: IllegalArgumentException) {
                    throw PropertiesBadRequestException(exception)
                }
            }
        }
    }
}