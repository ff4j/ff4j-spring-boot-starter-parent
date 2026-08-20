/*-
 * #%L
 * ff4j-spring-boot-autoconfigure-common
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
package org.ff4j.spring.boot.autoconfigure.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Unit level assertions on [FF4JConfiguration]: the Spring context tests
 * (`FF4JAuditEnableConfigurationTest` / `FF4JAuditDisableConfigurationTest`) already prove the bean
 * is wired, this asserts on the audit flag itself and on the flag actually being propagated to the
 * produced [org.ff4j.FF4j] instance in both states.
 */
class FF4JConfigurationUnitTest {

  @Test
  fun auditIsDisabledByDefaultAndTheProducedFF4JHasAuditOff() {
    val configuration = FF4JConfiguration()

    assertThat(configuration.isAuditEnabled).isFalse()
    assertThat(configuration.getFF4J().isEnableAudit).isFalse()
  }

  @Test
  fun theAuditFlagIsPropagatedToTheProducedFF4J() {
    val configuration = FF4JConfiguration()
    configuration.isAuditEnabled = true

    assertThat(configuration.isAuditEnabled).isTrue()
    assertThat(configuration.getFF4J().isEnableAudit).isTrue()
  }

  @Test
  fun eachInvocationProducesADistinctFF4JInstance() {
    val configuration = FF4JConfiguration()

    assertThat(configuration.getFF4J()).isNotSameAs(configuration.getFF4J())
  }
}
