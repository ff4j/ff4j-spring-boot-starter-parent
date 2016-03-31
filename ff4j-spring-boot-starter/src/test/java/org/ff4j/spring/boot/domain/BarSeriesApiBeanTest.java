package org.ff4j.spring.boot.domain;

import org.ff4j.audit.graph.BarSeries;
import org.ff4j.spring.boot.constants.CommonConstants;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class BarSeriesApiBeanTest {

    @Test
    public void testDefault() {
        BarSeriesApiBean bean = new BarSeriesApiBean();
        assertThat(bean.getColor()).isEqualTo(CommonConstants.HTML_WHITE);
        assertThat(bean.getValues()).isEmpty();
        assertThat(bean.getLabel()).isEqualTo(CommonConstants.N_A);
    }

    @Test
    public void testWhenPieSector() {
        BarSeries barSeries = new BarSeries("login", "CCCCCC", 1);
        BarSeriesApiBean bean = new BarSeriesApiBean(barSeries);
        assertThat(bean.getColor()).isEqualTo("CCCCCC");
        assertThat(bean.getValues()).isNotEmpty().contains(0.0);
        assertThat(bean.getLabel()).isEqualTo("login");
    }
}
