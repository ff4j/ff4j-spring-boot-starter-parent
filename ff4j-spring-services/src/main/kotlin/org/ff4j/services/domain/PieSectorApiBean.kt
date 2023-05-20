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

import org.ff4j.audit.chart.Serie
import org.ff4j.services.constants.CommonConstants
import java.io.Serializable

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
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
