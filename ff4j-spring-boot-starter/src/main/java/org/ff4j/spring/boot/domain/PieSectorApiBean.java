package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.audit.graph.PieSector;
import org.ff4j.spring.boot.constants.CommonConstants;

import java.io.Serializable;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class PieSectorApiBean implements Serializable {

    private static final long serialVersionUID = -8998722757094848417L;

    @Getter
    private String label = CommonConstants.N_A;

    @Getter
    private double value = 0.0;

    @Getter
    private String color = CommonConstants.HTML_WHITE;

    public PieSectorApiBean() {
        super();
    }

    public PieSectorApiBean(PieSector sector) {
        this.label = sector.getLabel();
        this.value = sector.getValue();
        this.color = sector.getColor();
    }
}
