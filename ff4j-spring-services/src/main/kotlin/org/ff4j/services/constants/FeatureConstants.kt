/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2026 FF4J
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
package org.ff4j.services.constants

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
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
  const val RESOURCE_MONITORING = "/monitoring"
  const val RESOURCE_FF4J_MONITORING = "$RESOURCE_FF4J$RESOURCE_MONITORING"
}
