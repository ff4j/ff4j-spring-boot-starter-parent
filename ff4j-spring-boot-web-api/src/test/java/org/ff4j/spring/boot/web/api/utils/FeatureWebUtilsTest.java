package org.ff4j.spring.boot.web.api.utils;

/*-
 * #%L
 * ff4j-spring-boot-web-api
 * %%
 * Copyright (C) 2013 - 2019 FF4J
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

import org.ff4j.services.model.FeatureActions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureWebUtilsTest {

  @Test
  public void giveTheActionIsCreateWhenAskedForHttpStatusShouldReturn201_CREATED() {
    ResponseEntity<Boolean> actualResponse = FeatureWebUtils.INSTANCE
        .getBooleanResponseEntityByHttpStatus(FeatureActions.CREATED);
    assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  public void giveTheActionIsUpdateWhenAskedForHttpStatusShouldReturn202_ACCEPTED() {
    ResponseEntity<Boolean> actualResponse = FeatureWebUtils.INSTANCE
        .getBooleanResponseEntityByHttpStatus(FeatureActions.UPDATED);
    assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
  }
}
