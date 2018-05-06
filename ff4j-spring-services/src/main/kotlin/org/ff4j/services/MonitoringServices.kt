package org.ff4j.services

import org.ff4j.FF4j
import org.ff4j.services.domain.EventRepositoryApiBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
class MonitoringServices(@Autowired val fF4j: FF4j) {

    fun getMonitoringStatus(): EventRepositoryApiBean {
        return EventRepositoryApiBean(fF4j.eventRepository)
    }

    fun getMonitoringStatus(start: Long, end: Long): EventRepositoryApiBean {
        return EventRepositoryApiBean(fF4j.eventRepository, start, end)
    }
}