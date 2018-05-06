package org.ff4j.services.domain

import org.ff4j.property.Property
import org.ff4j.property.util.PropertyFactory
import java.util.*
import java.util.stream.Collectors

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class PropertyApiBean {

    companion object {
        private const val serialVersionUID = -5366099799518640405L
    }

    var name: String? = null
    var description: String? = null
    var type: String? = null
    var value: String? = null
    var fixedValues: MutableSet<String> = HashSet()

    constructor() : super()

    constructor(property: Property<*>) {
        property.let {
            this.name = it.name
            this.description = it.description
            this.type = it.type
            this.value = it.asString()
            it.fixedValues?.let {
                this.fixedValues.addAll(it.stream().map({ it.toString() }).collect(Collectors.toList()))
            }
        }
    }

    fun asProperty(): Property<*> {
        return PropertyFactory.createProperty(name, type, value, description, fixedValues)
    }
}