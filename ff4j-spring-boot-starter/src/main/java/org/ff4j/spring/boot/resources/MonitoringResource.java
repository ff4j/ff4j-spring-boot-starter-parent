package org.ff4j.spring.boot.resources;

import org.ff4j.spring.boot.services.MonitoringService;
import org.ff4j.spring.boot.domain.EventRepositoryApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.ff4j.web.FF4jWebConstants.PARAM_END;
import static org.ff4j.web.FF4jWebConstants.PARAM_START;
import static org.ff4j.spring.boot.constants.FeatureConstants.RESOURCE_FF4J_MONITORING;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RestController
@RequestMapping(value = RESOURCE_FF4J_MONITORING)
public class MonitoringResource {

    @Autowired
    private MonitoringService monitoringService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public EventRepositoryApiBean getMonitoringStatus(@RequestParam(value = PARAM_START, required = false) Long start, @RequestParam(value = PARAM_END, required = false) Long end) {
        return monitoringService.getMonitoringStatus(start, end);
    }
}
