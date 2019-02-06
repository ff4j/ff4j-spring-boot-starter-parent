/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */

package org.ff4j.spring.boot.autoconfigure

import org.ff4j.FF4j
import org.ff4j.web.FF4jDispatcherServlet
import org.ff4j.web.embedded.ConsoleServlet
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@Configuration
@ConditionalOnClass(ConsoleServlet::class, FF4jDispatcherServlet::class)
@AutoConfigureAfter(FF4JConfiguration::class)
class FF4JWebConfiguration : SpringBootServletInitializer() {

    @Bean
    fun servletRegistrationBean(ff4jConsoleServlet: ConsoleServlet): ServletRegistrationBean<*> {
        return ServletRegistrationBean(ff4jConsoleServlet, "/ff4j-console")
    }

    @Bean
    @ConditionalOnMissingBean
    fun getFF4jServlet(ff4j: FF4j): ConsoleServlet {
        val ff4jConsoleServlet = ConsoleServlet()
        ff4jConsoleServlet.ff4j = ff4j
        return ff4jConsoleServlet
    }

    @Bean
    fun ff4jDispatcherServletRegistrationBean(ff4jDispatcherServlet: FF4jDispatcherServlet): ServletRegistrationBean<*> {
        return ServletRegistrationBean(ff4jDispatcherServlet, "/ff4j-web-console/*")
    }

    @Bean
    @ConditionalOnMissingBean
    fun getFF4jDispatcherServlet(ff4j: FF4j): FF4jDispatcherServlet {
        val ff4jConsoleServlet = FF4jDispatcherServlet()
        ff4jConsoleServlet.ff4j = ff4j
        return ff4jConsoleServlet
    }
}

