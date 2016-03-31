package org.ff4j.spring.boot.services;

import org.ff4j.FF4j;
import org.ff4j.spring.boot.domain.EventRepositoryApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@Service
public class MonitoringService {
    @Autowired
    private FF4j ff4j;

    public EventRepositoryApiBean getMonitoringStatus(Long start, Long end) {
        return new EventRepositoryApiBean(ff4j.getEventRepository(), start, end);
    }
}
