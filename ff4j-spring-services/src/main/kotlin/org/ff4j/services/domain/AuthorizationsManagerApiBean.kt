package org.ff4j.services.domain

import org.apache.commons.lang3.StringUtils.EMPTY
import org.ff4j.security.AuthorizationsManager
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class AuthorizationsManagerApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 6547399670614500217L
    }

    var type: String = EMPTY
    var permissions: MutableSet<String> = HashSet()

    constructor() : super()

    constructor(authorizationsManager: AuthorizationsManager) {
        this.type = authorizationsManager.javaClass.canonicalName
        this.permissions.addAll(authorizationsManager.listAllPermissions())
    }
}