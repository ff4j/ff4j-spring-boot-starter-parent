package org.ff4j.services.domain

import org.ff4j.audit.chart.Serie
import org.ff4j.services.constants.CommonConstants.HTML_WHITE
import org.ff4j.services.constants.CommonConstants.N_A
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class BarSeriesApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 8703972617439641703L
    }

    var label: String = N_A
    var color: String = HTML_WHITE
    var value: Double = 0.0

    constructor() : super()

    constructor(barSeries: Serie<Double>) {
        this.label = barSeries.label
        this.color = barSeries.color
        this.value = barSeries.value
    }
}