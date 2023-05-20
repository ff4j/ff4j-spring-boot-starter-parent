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

import org.assertj.core.api.Assertions
import org.ff4j.services.constants.CommonConstants
import org.junit.Test

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class BarSeriesApiBeanTest {

  @Test
  fun testDefault() {
    val bean = BarSeriesApiBean()
    Assertions.assertThat(bean.color).isEqualTo(CommonConstants.HTML_WHITE)
    Assertions.assertThat(bean.label).isEqualTo(CommonConstants.N_A)
  }
}
