package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.FF4j;

import java.io.Serializable;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class FF4jStatusApiBean implements Serializable {

    private static final long serialVersionUID = 3126369513162358650L;

    @Getter
    private String uptime;

    @Getter
    private boolean autocreate;

    @Getter
    private String version = "N/A";

    @Getter
    private FeatureStoreApiBean featuresStore;

    @Getter
    private EventRepositoryApiBean eventRepository;

    @Getter
    private AuthorizationsManagerApiBean authorizationsManager;

    public FF4jStatusApiBean(FF4j ff4j) {
        // UpTime
        long up = System.currentTimeMillis() - ff4j.getStartTime();
        long daynumber = up / (1000 * 3600 * 24);
        up = up - (daynumber * 1000 * 3600 * 24);
        long hourNumber = up / (1000 * 3600);
        up = up - (hourNumber * 1000 * 3600);
        long minutenumber = up / (1000 * 60);
        up = up - (minutenumber * 1000 * 60);
        long secondnumber = up / 1000;
        uptime = daynumber + " day(s) ";
        uptime += hourNumber + " hours(s) ";
        uptime += minutenumber + " minute(s) ";
        uptime += secondnumber + " seconds\"";
        autocreate = ff4j.isAutocreate();
        version = ff4j.getVersion();
        if (null != ff4j.getFeatureStore()) {
            featuresStore = new FeatureStoreApiBean(ff4j.getFeatureStore());
        }
        if (null != ff4j.getEventRepository()) {
            eventRepository = new EventRepositoryApiBean(ff4j.getEventRepository(), null, null);
        }
        if (null != ff4j.getAuthorizationsManager()) {
            authorizationsManager = new AuthorizationsManagerApiBean(ff4j.getAuthorizationsManager());
        }
    }
}
