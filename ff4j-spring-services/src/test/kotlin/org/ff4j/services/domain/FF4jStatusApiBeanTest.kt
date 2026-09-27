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
import org.junit.jupiter.api.Test

/**
 * Unit tests for [FF4jStatusApiBean]'s uptime arithmetic and field mapping.
 */
class FF4jStatusApiBeanTest {

  /** 1 day, 2 hours, 3 minutes, 4 seconds, plus a 500ms margin so the seconds digit is stable. */
  private val elapsedMillis = (((1L * 24 + 2) * 60 + 3) * 60 + 4) * 1000 + 500

  @Test
  fun uptimeIsBrokenDownIntoDaysHoursMinutesSeconds() {
    val ff4j = object : FF4j() {
      override fun getStartTime(): Long = System.currentTimeMillis() - elapsedMillis
    }

    val status = FF4jStatusApiBean(ff4j)

    assertThat(status.uptime).isEqualTo("1 day(s) 2 hours(s) 3 minute(s) 4 seconds")
  }

  @Test
  fun freshInstanceHasZeroUptime() {
    val ff4j = object : FF4j() {
      override fun getStartTime(): Long = System.currentTimeMillis()
    }

    assertThat(FF4jStatusApiBean(ff4j).uptime).isEqualTo("0 day(s) 0 hours(s) 0 minute(s) 0 seconds")
  }

  @Test
  fun fieldsAreCopiedFromFF4j() {
    val ff4j = FF4j()
    ff4j.isAutocreate = true

    val status = FF4jStatusApiBean(ff4j)

    assertThat(status.autocreate).isTrue
    assertThat(status.version).isEqualTo(ff4j.version)
    assertThat(status.featuresStore).isNotNull
    assertThat(status.featuresStore!!.type).isEqualTo(ff4j.featureStore.javaClass.canonicalName)
  }

  @Test
  fun autocreateDefaultsToFalse() {
    assertThat(FF4jStatusApiBean(FF4j()).autocreate).isFalse
  }
}
