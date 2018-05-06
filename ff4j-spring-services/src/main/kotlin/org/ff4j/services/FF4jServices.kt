package org.ff4j.services

import org.ff4j.FF4j
import org.ff4j.core.FlippingExecutionContext
import org.ff4j.services.domain.AuthorizationsManagerApiBean
import org.ff4j.services.domain.FF4jStatusApiBean
import org.ff4j.services.exceptions.AuthorizationNotExistsException
import org.ff4j.services.validator.FeatureValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class FF4jServices(@Autowired val fF4j: FF4j, @Autowired val ff4jValidator: FeatureValidator) {

    fun getStatus(): FF4jStatusApiBean {
        return FF4jStatusApiBean(this.fF4j)
    }

    fun getSecurityInfo(): AuthorizationsManagerApiBean {
        return fF4j.authorizationsManager?.let {
            AuthorizationsManagerApiBean(it)
        } ?: run {
            throw AuthorizationNotExistsException()
        }
    }

    fun check(featureUID: String): Boolean {
        ff4jValidator.assertFeatureExists(featureUID)
        return fF4j.check(featureUID)
    }

    fun check(featureUID: String, params: MutableMap<String, String>): Boolean {
        ff4jValidator.assertFeatureExists(featureUID)
        val flipExecCtx = FlippingExecutionContext()
        params.entries.forEach({ flipExecCtx.putString(it.key, it.value) })
        return fF4j.check(featureUID, flipExecCtx)
    }
}