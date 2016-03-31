package org.ff4j.spring.boot.domain;

import lombok.Getter;
import lombok.Setter;
import org.ff4j.core.FlippingStrategy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class FlippingStrategyApiBean implements Serializable {

    private static final long serialVersionUID = 2257391205134600598L;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Map<String, String> initParams = new HashMap<>();

    public FlippingStrategyApiBean() {
        super();
    }

    public FlippingStrategyApiBean(FlippingStrategy fs) {
        this.type = fs.getClass().getCanonicalName();
        this.initParams = fs.getInitParams();
    }
}
