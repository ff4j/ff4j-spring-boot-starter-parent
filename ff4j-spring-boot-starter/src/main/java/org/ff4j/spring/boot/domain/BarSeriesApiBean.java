package org.ff4j.spring.boot.domain;

import lombok.Getter;
import org.ff4j.audit.graph.BarSeries;
import org.ff4j.spring.boot.constants.CommonConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class BarSeriesApiBean implements Serializable {

    private static final long serialVersionUID = 8703972617439641703L;

    @Getter
    private String label = CommonConstants.N_A;

    @Getter
    private String color = CommonConstants.HTML_WHITE;

    @Getter
    private List<Double> values = new ArrayList<>();

    public BarSeriesApiBean() {
        super();
    }

    public BarSeriesApiBean(BarSeries barSeries) {
        this.label = barSeries.getLabel();
        this.color = barSeries.getColor();
        this.values = barSeries.getValues();
    }
}
