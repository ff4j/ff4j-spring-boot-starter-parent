package org.ff4j.spring.boot.web.api.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.ff4j.FF4j;
import org.ff4j.spring.boot.autoconfigure.FF4JConfiguration;
import org.ff4j.spring.boot.web.api.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {Application.class, FF4JConfiguration.class})
@TestPropertySource(properties = {"ff4j.audit.enabled=false"})
public class FF4JAuditDisableConfigurationTest {

  @Autowired
  private FF4j ff4j;

  @Test
  void testBoot() {
    assertThat(true).isTrue();
  }

  @Test
  void testAuditEnable() {
    assertThat(ff4j.isEnableAudit()).isFalse();
  }
}