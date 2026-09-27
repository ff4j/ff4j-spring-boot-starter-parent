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
package org.ff4j.services

import org.assertj.core.api.Assertions.assertThat
import org.ff4j.FF4j
import org.ff4j.audit.repository.InMemoryEventRepository
import org.junit.jupiter.api.Test

/**
 * Unit tests for [MonitoringServices] against a real in-memory FF4j instance.
 */
class MonitoringServicesTest {

  private val ff4j = FF4j().also { it.eventRepository = InMemoryEventRepository() }

  @Test
  fun getMonitoringStatusDescribesTheEventRepository() {
    val status = MonitoringServices(ff4j).getMonitoringStatus().block()!!

    assertThat(status.type).isEqualTo(InMemoryEventRepository::class.java.canonicalName)
    assertThat(status.eventsPie).isNotNull
    assertThat(status.barChart).isNotNull
    assertThat(status.hitCount).isZero()
  }

  @Test
  fun getMonitoringStatusForAPeriodDescribesTheEventRepository() {
    val end = System.currentTimeMillis()
    val start = end - 60_000

    val status = MonitoringServices(ff4j).getMonitoringStatus(start, end).block()!!

    assertThat(status.type).isEqualTo(InMemoryEventRepository::class.java.canonicalName)
    assertThat(status.eventsPie).isNotNull
    assertThat(status.barChart).isNotNull
    assertThat(status.hitCount).isZero()
  }
}
