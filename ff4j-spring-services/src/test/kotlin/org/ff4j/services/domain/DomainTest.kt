/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2022 FF4J
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
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class DomainTest {

  private val BEAN_TESTER = BeanTester()

  @Test
  fun getterAndSetterCorrectness() {
    Assert.assertTrue(true)
    BEAN_TESTER.testBean(FeatureApiBean::class.java)
    BEAN_TESTER.testBean(FeatureStoreApiBean::class.java)
    BEAN_TESTER.testBean(PropertyStoreApiBean::class.java)
    BEAN_TESTER.testBean(FlippingStrategyApiBean::class.java)
    BEAN_TESTER.testBean(BarChartApiBean::class.java)
    BEAN_TESTER.testBean(PieChartApiBean::class.java)
    BEAN_TESTER.testBean(AuthorizationsManagerApiBean::class.java)
    BEAN_TESTER.testBean(PropertyApiBean::class.java)
    BEAN_TESTER.testBean(FF4jStatusApiBean::class.java)
    BEAN_TESTER.testBean(EventRepositoryApiBean::class.java)
    BEAN_TESTER.testBean(CacheApiBean::class.java)
    BEAN_TESTER.testBean(GroupDescApiBean::class.java)
    BEAN_TESTER.testBean(BarSeriesApiBean::class.java)
    BEAN_TESTER.testBean(PieSectorApiBean::class.java)
  }
}
