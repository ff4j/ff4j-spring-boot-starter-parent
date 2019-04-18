package org.ff4j.spring.boot.web.api.config;

import org.junit.Assert;
import org.junit.Test;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfigTest {

    @Test
    public void testDocketCreation() {
        FF4jSwaggerConfig fixture = new FF4jSwaggerConfig();
        Docket docket = fixture.api();
        Assert.assertNotNull(docket);
    }
}
