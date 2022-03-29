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
import org.ff4j.core.Feature
import org.ff4j.services.domain.FeatureApiBean
import org.ff4j.services.validator.FeatureValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class GroupServices(@Autowired val fF4j: FF4j, @Autowired val ff4jValidator: FeatureValidator) {

    fun getFeaturesByGroup(groupName: String): Collection<FeatureApiBean> {
        ff4jValidator.assertGroupExist(groupName)
        return fF4j.featureStore.readGroup(groupName).values.stream().map { feature: Feature -> FeatureApiBean(feature) }
                .collect(Collectors.toList())
    }

    fun enableGroup(groupName: String) {
        ff4jValidator.assertGroupExist(groupName)
        fF4j.featureStore.enableGroup(groupName)
    }

    fun disableGroup(groupName: String) {
        ff4jValidator.assertGroupExist(groupName)
        fF4j.featureStore.disableGroup(groupName)
    }
}
