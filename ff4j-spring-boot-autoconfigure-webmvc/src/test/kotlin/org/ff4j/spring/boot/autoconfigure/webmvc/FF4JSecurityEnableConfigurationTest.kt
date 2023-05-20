/*-
 * #%L
 * ff4j-spring-boot-autoconfigure-webmvc
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
package org.ff4j.spring.boot.autoconfigure.webmvc

import org.assertj.core.api.Assertions
import org.ff4j.FF4j
import org.ff4j.security.SpringSecurityAuthorisationManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = [Application::class]
)
@TestPropertySource(properties = ["ff4j.security.enabled=true"])
class FF4JSecurityEnableConfigurationTest {

  @Autowired
  private lateinit var ff4j: FF4j

  @Test
  fun testBoot() {
    Assertions.assertThat(true).isTrue
  }

  @Test
  fun testAuditEnable() {
    Assertions.assertThat(ff4j.authorizationsManager).isInstanceOf(
      SpringSecurityAuthorisationManager::class.java
    )
  }
}
