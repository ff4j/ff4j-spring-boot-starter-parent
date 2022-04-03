package org.ff4j.services.domain;

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

import static org.assertj.core.api.Assertions.assertThat;

import org.ff4j.services.constants.CommonConstants;
import org.junit.Test;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class PieSectorApiBeanTest {

  @Test
  public void testDefault() {
    PieSectorApiBean bean = new PieSectorApiBean();
    assertThat(bean.getColor()).isEqualTo(CommonConstants.HTML_WHITE);
    assertThat(bean.getValue()).isEqualTo(0);
    assertThat(bean.getLabel()).isEqualTo(CommonConstants.N_A);
  }
}
