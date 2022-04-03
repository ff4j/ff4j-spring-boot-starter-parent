/*-
 * #%L
 * ff4j-spring-boot-autoconfigure
 * %%
 * Copyright (C) 2013 - 2022 FF4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.ff4j.spring.boot.autoconfigure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories

@Configuration
@ConditionalOnProperty(
  value = ["ff4j.web-console.security.enabled"], havingValue = "true", matchIfMissing = false
)
@AutoConfigureAfter(FF4JConfiguration::class)
@EnableWebSecurity
class FF4JSecurityConfiguration : WebSecurityConfigurerAdapter() {

  @Autowired
  lateinit var ff4jSecurityConfigurationProperties: FF4JSecurityConfigurationProperties

  @Value("\${ff4j.web-console.context-path:/ff4j-web-console}")
  private val contextPath: String? = null

  override fun configure(auth: AuthenticationManagerBuilder?) {
    val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    auth?.inMemoryAuthentication()?.withUser(ff4jSecurityConfigurationProperties.username)
      ?.password(encoder.encode(ff4jSecurityConfigurationProperties.password))?.roles("ADMIN")
  }

  override fun configure(http: HttpSecurity?) {
    http?.authorizeRequests()?.antMatchers("/")?.permitAll()?.antMatchers("$contextPath/**")?.hasRole("ADMIN")?.and()?.formLogin()
  }
}

