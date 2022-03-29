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

import org.ff4j.FF4j
import org.ff4j.services.constants.CommonConstants.N_A
import java.io.Serializable

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
class FF4jStatusApiBean : Serializable {

    companion object {
        private const val serialVersionUID = 3126369513162358650L
    }

    var uptime: String? = null
    var autocreate: Boolean = false
    var version: String = N_A
    var featuresStore: FeatureStoreApiBean? = null
    var eventRepository: EventRepositoryApiBean? = null
    var authorizationsManager: AuthorizationsManagerApiBean? = null

    constructor() : super()

    constructor(ff4j: FF4j) {
        var up = System.currentTimeMillis() - ff4j.startTime
        val day = up / (1000 * 3600 * 24)
        up -= day * 1000 * 3600 * 24
        val hourNumber = up / (1000 * 3600)
        up -= hourNumber * 1000 * 3600
        val minute = up / (1000 * 60)
        up -= minute * 1000 * 60
        val second = up / 1000
        uptime = day.toString() + " day(s) "
        uptime += hourNumber.toString() + " hours(s) "
        uptime += minute.toString() + " minute(s) "
        uptime += second.toString() + " seconds\""
        autocreate = ff4j.isAutocreate
        version = ff4j.version
        ff4j.featureStore?.let {
            this.featuresStore = FeatureStoreApiBean(it)
        }

        ff4j.eventRepository?.let {
            this.eventRepository = EventRepositoryApiBean(it)
        }

        ff4j.authorizationsManager?.let {
            this.authorizationsManager = AuthorizationsManagerApiBean(it)
        }
    }
}
