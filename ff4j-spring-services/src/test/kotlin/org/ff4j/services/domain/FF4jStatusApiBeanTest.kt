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
package org.ff4j.services.domain

import org.assertj.core.api.Assertions.assertThat
import org.ff4j.FF4j
import org.ff4j.services.constants.CommonConstants.N_A
import org.ff4j.services.representation.TestAuthorizationsManager
import org.junit.jupiter.api.Test

/**
 * The uptime string is built from a chain of long divisions/subtractions, none of which was
 * previously asserted on. Pinning the FF4J start time makes the whole computation deterministic, so
 * the rendered string can be asserted exactly rather than smoke-tested.
 */
class FF4jStatusApiBeanTest {

  /**
   * FF4J exposes no start-time setter, so the value is pinned by overriding the getter — a
   * test-only subclass, no production change.
   */
  private class PinnedStartTimeFF4j(private val pinnedStartTime: Long) : FF4j() {
    override fun getStartTime(): Long = pinnedStartTime
  }

  private companion object {
    const val ONE_SECOND = 1000L
    const val ONE_MINUTE = 60 * ONE_SECOND
    const val ONE_HOUR = 60 * ONE_MINUTE
    const val ONE_DAY = 24 * ONE_HOUR

    /**
     * The half-second remainder keeps the rendered `seconds` component stable regardless of how
     * long the test itself takes to reach the assertion.
     */
    const val SETTLING_MARGIN = 500L
  }

  private fun ff4jStartedAgo(elapsed: Long) = PinnedStartTimeFF4j(System.currentTimeMillis() - elapsed)

  @Test
  fun uptimeRendersEveryComponentOfTheElapsedTime() {
    val elapsed = ONE_DAY + 2 * ONE_HOUR + 3 * ONE_MINUTE + 4 * ONE_SECOND + SETTLING_MARGIN

    val bean = FF4jStatusApiBean(ff4jStartedAgo(elapsed))

    assertThat(bean.uptime).isEqualTo("1 day(s) 2 hours(s) 3 minute(s) 4 seconds")
  }

  @Test
  fun uptimeRollsOverIntoDaysHoursAndMinutes() {
    val elapsed = 12 * ONE_DAY + 23 * ONE_HOUR + 59 * ONE_MINUTE + 59 * ONE_SECOND + SETTLING_MARGIN

    val bean = FF4jStatusApiBean(ff4jStartedAgo(elapsed))

    assertThat(bean.uptime).isEqualTo("12 day(s) 23 hours(s) 59 minute(s) 59 seconds")
  }

  @Test
  fun uptimeIsAllZeroesForAJustStartedInstance() {
    val bean = FF4jStatusApiBean(ff4jStartedAgo(SETTLING_MARGIN))

    assertThat(bean.uptime).isEqualTo("0 day(s) 0 hours(s) 0 minute(s) 0 seconds")
  }

  @Test
  fun theStatusCarriesTheFF4JFlagsAndStores() {
    val ff4j = FF4j()
    ff4j.isAutocreate = true
    ff4j.authorizationsManager = TestAuthorizationsManager("USER", "USER,ADMIN")

    val bean = FF4jStatusApiBean(ff4j)

    assertThat(bean.autocreate).isTrue()
    assertThat(bean.version).isEqualTo(ff4j.version)
    assertThat(bean.version).isNotBlank()
    assertThat(bean.featuresStore).isNotNull()
    assertThat(bean.eventRepository).isNotNull()
    assertThat(bean.authorizationsManager).isNotNull()
  }

  @Test
  fun autocreateDefaultsToFalseOnTheStatus() {
    val bean = FF4jStatusApiBean(FF4j())

    assertThat(bean.autocreate).isFalse()
  }

  @Test
  fun theNoArgConstructorLeavesTheStatusEmpty() {
    val bean = FF4jStatusApiBean()

    assertThat(bean.uptime).isNull()
    assertThat(bean.autocreate).isFalse()
    assertThat(bean.version).isEqualTo(N_A)
    assertThat(bean.featuresStore).isNull()
    assertThat(bean.eventRepository).isNull()
    assertThat(bean.authorizationsManager).isNull()
  }
}
