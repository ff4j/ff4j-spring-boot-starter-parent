package org.ff4j.spring.boot.autoconfigure;

/*-
 * #%L
 * ff4j-spring-boot-autoconfigure
 * %%
 * Copyright (C) 2013 - 2022 FF4J
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

import static org.assertj.core.api.Assertions.assertThat;

import org.ff4j.FF4j;
import org.ff4j.security.SpringSecurityAuthorisationManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {Application.class, FF4JConfiguration.class})
@TestPropertySource(properties = {"ff4j.security.enabled=false"})
public class FF4JSecurityDisableConfigurationTest {

  @Autowired
  private FF4j ff4j;

  @Test
  void testBoot() {
    assertThat(true).isTrue();
  }

  @Test
  void testAuditEnable() {
    assertThat(ff4j.getAuthorizationsManager()).isNull();
  }
}
