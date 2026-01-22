/*-
 * #%L
 * ff4j-spring-rest-api
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
package org.ff4j.spring.rest.api.representation

import org.apache.commons.lang3.StringUtils
import org.ff4j.security.AbstractAuthorizationManager
import java.util.*

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class TestAuthorizationsManager(
  private val currentUserPermissions: String,
  private val allPermissions: String,
  private val currentUserName: String = ""
) : AbstractAuthorizationManager() {

  override fun getCurrentUserName(): String = currentUserName

  override fun getCurrentUserPermissions(): MutableSet<String> =
    if (StringUtils.isBlank(currentUserPermissions)) Collections.emptySet() else HashSet(
      currentUserPermissions.split(",")
    )

  override fun listAllPermissions(): MutableSet<String>? =
    if (StringUtils.isBlank(allPermissions)) Collections.emptySet() else HashSet(
      allPermissions.split(",")
    )
}
