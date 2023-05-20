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
package org.ff4j.services.domain

import org.ff4j.audit.EventQueryDefinition
import org.ff4j.audit.repository.EventRepository
import java.io.Serializable

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class EventRepositoryApiBean : Serializable {

  companion object {
    private const val serialVersionUID = -3365322115944400241L
  }

  var type: String? = null
  lateinit var eventsPie: PieChartApiBean
  lateinit var barChart: BarChartApiBean
  var hitCount: Int = 0

  // for DomainTest
  constructor()

  constructor(eventRepository: EventRepository) {
    initialize(eventRepository, EventQueryDefinition())
  }

  constructor(eventRepository: EventRepository, start: Long, end: Long) {
    initialize(eventRepository, EventQueryDefinition(start, end))
  }

  private fun initialize(eventRepository: EventRepository, query: EventQueryDefinition) {
    this.type = eventRepository.javaClass.canonicalName
    this.eventsPie = PieChartApiBean(eventRepository.getFeatureUsagePieChart(query))
    this.barChart = BarChartApiBean(eventRepository.getFeatureUsageBarChart(query))
    eventsPie.sectors.forEach { hitCount.plus(it.value) }
  }
}
