/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2023 FF4J
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
package org.ff4j.services.validator

import org.apache.commons.lang3.StringUtils
import org.ff4j.FF4j
import org.ff4j.services.exceptions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FeatureValidator {
  @Autowired
  lateinit var ff4j: FF4j

  fun assertFeatureIdsMatch(featureUID: String?, featureApiUID: String?) {
    if (featureUID != featureApiUID) {
      throw FeatureIdNotMatchException()
    }
  }

  fun assertFeatureUIDIsNotBlank(featureUID: String?) {
    if (StringUtils.isBlank(featureUID)) {
      throw FeatureIdBlankException()
    }
  }

  fun assertFeatureExists(featureUID: String) {
    if (!ff4j.exist(featureUID)) {
      throw FeatureNotFoundException()
    }
  }

  fun assertRoleDoesNotExist(featureUID: String, role: String) {
    if (ff4j.featureStore.read(featureUID).permissions.contains(role)) {
      throw RoleExistsException()
    }
  }

  fun assertRoleExist(featureUID: String, role: String) {
    if (!ff4j.featureStore.read(featureUID).permissions.contains(role)) {
      throw RoleNotExistsException()
    }
  }

  fun assertGroupDoesNotExist(groupName: String) {
    if (ff4j.featureStore.existGroup(groupName)) {
      throw GroupExistsException()
    }
  }

  fun assertGroupExist(groupName: String) {
    if (!ff4j.featureStore.existGroup(groupName)) {
      throw GroupNotExistsException()
    }
  }
}
