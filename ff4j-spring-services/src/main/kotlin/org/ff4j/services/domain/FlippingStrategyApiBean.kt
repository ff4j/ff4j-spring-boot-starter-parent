package org.ff4j.services.domain

import org.ff4j.core.FlippingStrategy
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FlippingStrategyApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 2257391205134600598L
    }

    var type: String? = null
    var initParams: MutableMap<String, String> = HashMap()

    constructor() : super()

    constructor(flippingStrategy: FlippingStrategy) {
        type = flippingStrategy.javaClass.canonicalName
        initParams = flippingStrategy.initParams
    }
}