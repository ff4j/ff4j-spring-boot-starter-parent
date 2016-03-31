package org.ff4j.spring.boot.domain;

import lombok.Getter;
import org.ff4j.audit.graph.PieChart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class PieChartApiBean implements Serializable {

    private static final long serialVersionUID = 3177966921214178831L;

    @Getter
    private String title;

    @Getter
    private List<PieSectorApiBean> sectors = new ArrayList<>();

    public PieChartApiBean(PieChart pie) {
        title = pie.getTitle();
        sectors.addAll(pie.getSectors().stream().map(PieSectorApiBean::new).collect(Collectors.toList()));
    }
}
