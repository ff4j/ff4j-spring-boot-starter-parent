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

import org.junit.Assert
import org.junit.Test
import org.meanbean.test.BeanTester

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class DomainTest {

  private val beanTester = BeanTester()

  @Test
  fun getterAndSetterCorrectness() {
    Assert.assertTrue(true)
    beanTester.testBean(FeatureApiBean::class.java)
    beanTester.testBean(FeatureStoreApiBean::class.java)
    beanTester.testBean(PropertyStoreApiBean::class.java)
    beanTester.testBean(FlippingStrategyApiBean::class.java)
    beanTester.testBean(BarChartApiBean::class.java)
    beanTester.testBean(PieChartApiBean::class.java)
    beanTester.testBean(AuthorizationsManagerApiBean::class.java)
    beanTester.testBean(PropertyApiBean::class.java)
    beanTester.testBean(FF4jStatusApiBean::class.java)
    beanTester.testBean(EventRepositoryApiBean::class.java)
    beanTester.testBean(CacheApiBean::class.java)
    beanTester.testBean(GroupDescApiBean::class.java)
    beanTester.testBean(BarSeriesApiBean::class.java)
    beanTester.testBean(PieSectorApiBean::class.java)
  }
}
