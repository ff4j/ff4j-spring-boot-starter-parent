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
import org.ff4j.audit.Event
import org.ff4j.audit.EventConstants
import org.ff4j.audit.repository.InMemoryEventRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * The Cucumber suite only drives the no-argument overload; the time-windowed
 * `getMonitoringStatus(start, end)` overload had no test at all. Both are asserted here on the
 * emitted [org.ff4j.services.domain.EventRepositoryApiBean], including the window actually being
 * applied (an event outside the requested window must not show up in the chart).
 */
class MonitoringServicesUnitTest {

  private companion object {
    const val ONE_HOUR = 60 * 60 * 1000L
    const val ONE_DAY = 24 * ONE_HOUR
    const val FEATURE_UID = "monitored-feature"
  }

  private lateinit var eventRepository: InMemoryEventRepository
  private lateinit var monitoringServices: MonitoringServices

  @BeforeEach
  fun setUp() {
    eventRepository = InMemoryEventRepository()
    val ff4j = FF4j()
    ff4j.eventRepository = eventRepository
    monitoringServices = MonitoringServices(ff4j)
    eventRepository.saveEvent(
      Event(EventConstants.SOURCE_JAVA, EventConstants.TARGET_FEATURE, FEATURE_UID, EventConstants.ACTION_CHECK_OK)
    )
  }

  @Test
  fun theMonitoringStatusDescribesTheEventRepositoryBackingFF4J() {
    val status = monitoringServices.getMonitoringStatus().block()!!

    assertThat(status.type).isEqualTo(InMemoryEventRepository::class.java.canonicalName)
    assertThat(status.eventsPie).isNotNull()
    assertThat(status.barChart).isNotNull()
  }

  @Test
  fun theMonitoringStatusReportsAFeatureCheckedWithinTheRequestedWindow() {
    val now = System.currentTimeMillis()

    val status = monitoringServices.getMonitoringStatus(now - ONE_HOUR, now + ONE_HOUR).block()!!

    assertThat(status.type).isEqualTo(InMemoryEventRepository::class.java.canonicalName)
    assertThat(status.eventsPie.sectors.map { it.label }).contains(FEATURE_UID)
    assertThat(status.eventsPie.sectors.single { it.label == FEATURE_UID }.value).isEqualTo(1)
  }

  @Test
  fun theMonitoringStatusIgnoresEventsOutsideTheRequestedWindow() {
    val now = System.currentTimeMillis()

    val status = monitoringServices.getMonitoringStatus(now - 2 * ONE_DAY, now - ONE_DAY).block()!!

    assertThat(status.eventsPie.sectors.map { it.label }).doesNotContain(FEATURE_UID)
  }

  @Test
  fun eachCallProducesAFreshStatusSnapshot() {
    val first = monitoringServices.getMonitoringStatus().block()!!
    val second = monitoringServices.getMonitoringStatus().block()!!

    assertThat(first).isNotSameAs(second)
    assertThat(first.type).isEqualTo(second.type)
  }
}
