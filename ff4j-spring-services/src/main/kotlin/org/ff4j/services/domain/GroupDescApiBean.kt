package org.ff4j.services.domain

import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class GroupDescApiBean : Serializable {


    companion object {
        private const val serialVersionUID = -7339190302097692175L
    }

    var groupName: String? = null
    var features: MutableList<String> = ArrayList()

    constructor() : super()

    constructor(groupName: String, names: MutableList<String>) {
        this.groupName = groupName
        this.features = names
    }
}