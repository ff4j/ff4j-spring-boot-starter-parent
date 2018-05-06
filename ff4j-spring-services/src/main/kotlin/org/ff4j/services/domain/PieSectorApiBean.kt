package org.ff4j.services.domain

import org.ff4j.audit.chart.Serie
import org.ff4j.services.constants.CommonConstants
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class PieSectorApiBean : Serializable {

    companion object {
        private const val serialVersionUID = -8998722757094848417L
    }

    var label = CommonConstants.N_A
    var value = 0
    var color = CommonConstants.HTML_WHITE

    constructor() : super()

    constructor(sector: Serie<Int>) {
        this.label = sector.label
        this.value = sector.value
        this.color = sector.color
    }
}