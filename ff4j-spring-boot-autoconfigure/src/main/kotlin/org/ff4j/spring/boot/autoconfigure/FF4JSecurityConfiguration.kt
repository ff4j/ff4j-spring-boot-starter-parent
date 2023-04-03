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

import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@ConditionalOnProperty(
        value = ["ff4j.web-console.security.enabled"], havingValue = "true", matchIfMissing = false
)
@ConditionalOnBean(FF4JWebConsoleConfiguration::class)
@AutoConfigureAfter(FF4JWebConsoleConfiguration::class)
@EnableWebSecurity
class FF4JSecurityConfiguration(private val config: FF4JConfigurationProperties) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf()?.disable()?.authorizeHttpRequests()?.anyRequest()?.permitAll();
        return http.build();
    }

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager? {
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val user: UserDetails = User.builder().passwordEncoder(encoder::encode)
                .username(config.webConsole.security.username)
                .password(encoder.encode(config.webConsole.security.password))
                .roles("ADMIN")
                .build()
        return InMemoryUserDetailsManager(user)
    }
}

