package org.ff4j.services.constants

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
object FeatureConstants {
    // PATH PARAM
    const val PATH_PARAM_GROUP = "{groupName}"
    const val PATH_PARAM_NAME = "{name}"
    const val PATH_PARAM_ROLE = "{role}"
    const val PATH_PARAM_VALUE = "{value}"
    const val PATH_PARAM_UID = "{uid}"
    // PARAM
    const val PARAM_ROLE = "role"
    const val PARAM_GROUP = "groupName"
    const val PARAM_NAME = "name"
    const val PARAM_VALUE = "value"
    // RESOURCE
    const val ROOT = "/api/"
    const val RESOURCE_FF4J = ROOT + "ff4j"
    const val RESOURCE_STORE = "/store"
    const val RESOURCE_FEATURES = "/features"
    const val RESOURCE_FF4J_STORE_FEATURES = RESOURCE_FF4J + RESOURCE_STORE + RESOURCE_FEATURES
    const val RESOURCE_GROUPS = "/groups"
    const val RESOURCE_FF4J_STORE_GROUPS = RESOURCE_FF4J + RESOURCE_STORE + RESOURCE_GROUPS
    const val RESOURCE_PROPERTY_STORE = "/propertyStore"
    const val RESOURCE_PROPERTIES = "/properties"
    const val RESOURCE_PROPERTIES_STORE_PROPERTIES = RESOURCE_FF4J + RESOURCE_PROPERTY_STORE + RESOURCE_PROPERTIES
    const val RESOURCE_FF4J_PROPERTY_STORE = "$RESOURCE_FF4J$RESOURCE_PROPERTY_STORE"
    const val RESOURCE_CLEAR_CACHE = "/clearCache"
    const val RESOURCE_FF4J_STORE = RESOURCE_FF4J + RESOURCE_STORE
    const val RESOURCE_FF4J_MONITORING = "$RESOURCE_FF4J/monitoring"
}