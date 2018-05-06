package org.ff4j.services.domain

import org.ff4j.audit.chart.PieChart
import org.ff4j.audit.chart.Serie
import java.io.Serializable
import java.util.*

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class PieChartApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 3177966921214178831L
    }

    var title: String? = null
    var sectors: MutableList<PieSectorApiBean> = ArrayList()

    constructor() : super()

    constructor(pieChart: PieChart) {
        this.title = pieChart.title
        for (sector: Serie<Int> in pieChart.sectors) {
            this.sectors.add(PieSectorApiBean(sector))
        }
    }
}