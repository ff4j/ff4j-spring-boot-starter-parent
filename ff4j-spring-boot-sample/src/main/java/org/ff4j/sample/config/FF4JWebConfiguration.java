/*
package org.ff4j.sample.config;

import org.ff4j.FF4j;
import org.ff4j.web.embedded.ConsoleServlet;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

*/
/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 *//*

@Configuration
public class FF4JWebConfiguration extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FF4JWebConfiguration.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ConsoleServlet consoleServlet) {
        return new ServletRegistrationBean(consoleServlet, "/ff4j-console/");
    }

    @Bean
    public ConsoleServlet consoleServlet(FF4j ff4j, ServletConfig servletConfig) throws ServletException {
        ConsoleServlet consoleServlet = new ConsoleServlet();
        consoleServlet.setFf4j(ff4j);
        consoleServlet.init(servletConfig);
        return consoleServlet;
    }
}
*/
