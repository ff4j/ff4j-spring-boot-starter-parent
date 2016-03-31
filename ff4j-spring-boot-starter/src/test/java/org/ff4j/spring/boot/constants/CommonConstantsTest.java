package org.ff4j.spring.boot.constants;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class CommonConstantsTest {
    @Test
    public void constructorInvocation() {
        try {
            final Constructor<CommonConstants> c = CommonConstants.class.getDeclaredConstructor();
            c.setAccessible(true);
            final CommonConstants newInstance = c.newInstance();
            assertThat(newInstance).isNull();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            assertThat(e).hasCauseExactlyInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Test
    public void constantValuesTest() {
        assertThat(CommonConstants.ROOT).isEqualTo("/");
        assertThat(CommonConstants.N_A).isEqualTo("N/A");
        assertThat(CommonConstants.HTML_WHITE).isEqualTo("FFFFFF");
    }
}
