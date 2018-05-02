package org.ff4j.services.domain

import org.apache.commons.lang3.StringUtils.EMPTY
import org.ff4j.audit.chart.BarChart
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class BarChartApiBean : Serializable {

    companion object {
        private const val serialVersionUID = -4014082937210867980L
    }

    var title: String = EMPTY
    var labels: MutableList<BarSeriesApiBean> = ArrayList()

    constructor() : super()

    constructor(barChart: BarChart) {
        this.title = barChart.title
    }
}