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
package org.ff4j.services

import org.ff4j.FF4j
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.model.FeatureActions
import org.ff4j.services.validator.FeatureValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class FeatureServices(@Autowired val fF4j: FF4j, @Autowired val featureValidator: FeatureValidator) {
  fun getFeature(featureUID: String): Mono<FeatureApiBean> {
    featureValidator.assertFeatureExists(featureUID)
    return Mono.just(FeatureApiBean(fF4j.featureStore.read(featureUID)))
  }

  fun createOrUpdateFeature(featureUID: String, featureApiBean: FeatureApiBean): Mono<FeatureActions> {
    featureValidator.assertFeatureUIDIsNotBlank(featureApiBean.uid)
    featureValidator.assertFeatureIdsMatch(featureUID, featureApiBean.uid)
    return if (fF4j.featureStore.exist(featureUID)) {
      fF4j.featureStore.update(featureApiBean.toFeature())
      Mono.just(FeatureActions.UPDATED)
    } else {
      fF4j.featureStore.create(featureApiBean.toFeature())
      Mono.just(FeatureActions.CREATED)
    }
  }

  fun deleteFeature(featureUID: String) {
    featureValidator.assertFeatureExists(featureUID)
    fF4j.featureStore.delete(featureUID)
  }

  fun disableFeature(featureUID: String) {
    featureValidator.assertFeatureExists(featureUID)
    fF4j.featureStore.disable(featureUID)
  }

  fun enableFeature(featureUID: String) {
    featureValidator.assertFeatureExists(featureUID)
    fF4j.featureStore.enable(featureUID)
  }

  fun grantRoleToFeature(featureUID: String, roleName: String) {
    featureValidator.assertFeatureExists(featureUID)
    featureValidator.assertRoleDoesNotExist(featureUID, roleName)
    fF4j.featureStore.grantRoleOnFeature(featureUID, roleName)
  }

  fun removeRoleFromFeature(featureUID: String, roleName: String) {
    featureValidator.assertFeatureExists(featureUID)
    featureValidator.assertRoleExist(featureUID, roleName)
    fF4j.featureStore.removeRoleFromFeature(featureUID, roleName)
  }

  fun addGroupToFeature(featureUID: String, groupName: String) {
    featureValidator.assertFeatureExists(featureUID)
    featureValidator.assertGroupDoesNotExist(groupName)
    fF4j.featureStore.addToGroup(featureUID, groupName)
  }

  fun removeGroupFromFeature(featureUID: String, groupName: String) {
    featureValidator.assertFeatureExists(featureUID)
    featureValidator.assertGroupExist(groupName)
    fF4j.featureStore.removeFromGroup(featureUID, groupName)
  }
}
