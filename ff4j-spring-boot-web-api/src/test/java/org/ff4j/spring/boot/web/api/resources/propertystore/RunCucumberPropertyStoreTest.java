package org.ff4j.spring.boot.web.api.resources.propertystore;

/*-
 * #%L
 * ff4j-spring-boot-web-api
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

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.ff4j.spring.boot.autoconfigure.FF4JConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/PropertyStoreResource.feature",
    plugin = {"json:target/cucumber/PropertyStoreResource.json",
        "junit:target/cucumber/PropertyStoreResource.xml"},
    glue = "classpath:org/ff4j/spring/boot/web/api/resources/propertystore", tags = "@PropertyStoreResource")
@CucumberContextConfiguration
@SpringBootTest
@Import({FF4JConfiguration.class})
public class RunCucumberPropertyStoreTest {

}
