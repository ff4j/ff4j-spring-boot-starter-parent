package org.ff4j.spring.boot.domain;

import org.ff4j.audit.graph.PieSector;
import org.junit.Test;
import org.ff4j.spring.boot.constants.CommonConstants;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class PieSectorApiBeanTest {

    @Test
    public void testDefault() {
        PieSectorApiBean bean = new PieSectorApiBean();
        assertThat(bean.getColor()).isEqualTo(CommonConstants.HTML_WHITE);
        assertThat(bean.getValue()).isEqualTo(0.0);
        assertThat(bean.getLabel()).isEqualTo(CommonConstants.N_A);
    }

    @Test
    public void testWhenPieSector() {
        PieSector pieSector = new PieSector("login", 0.1, "CCCCCC");
        PieSectorApiBean bean = new PieSectorApiBean(pieSector);
        assertThat(bean.getColor()).isEqualTo("CCCCCC");
        assertThat(bean.getValue()).isEqualTo(0.1);
        assertThat(bean.getLabel()).isEqualTo("login");
    }
}
