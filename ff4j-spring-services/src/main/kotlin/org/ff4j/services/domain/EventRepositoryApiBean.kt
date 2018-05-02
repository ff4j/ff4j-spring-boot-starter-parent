package org.ff4j.services.domain

import org.ff4j.audit.EventQueryDefinition
import org.ff4j.audit.repository.EventRepository
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class EventRepositoryApiBean : Serializable {

    companion object {
        private const val serialVersionUID = -3365322115944400241L
    }

    var type: String? = null
    var eventsPie: PieChartApiBean? = null
    var barChart: BarChartApiBean? = null
    var hitCount: Int = 0

    constructor() : super()

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
        for (sector: PieSectorApiBean in eventsPie?.sectors!!) {
            this.hitCount.plus(sector.value)
        }
    }
}