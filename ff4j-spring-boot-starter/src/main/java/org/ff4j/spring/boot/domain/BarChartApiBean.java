package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.audit.graph.BarChart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class BarChartApiBean implements Serializable {

    private static final long serialVersionUID = -4014082937210867980L;

    @Getter
    private String title;

    @Getter
    private List<String> labels = new ArrayList<>();

    @Getter
    private List<BarSeriesApiBean> series = new ArrayList<>();

    public BarChartApiBean(BarChart barChart) {
        this.title = barChart.getTitle();
        this.labels = barChart.getLabels();
        series.addAll(barChart.getSeries().values().stream().map(BarSeriesApiBean::new).collect(Collectors.toList()));
    }
}
