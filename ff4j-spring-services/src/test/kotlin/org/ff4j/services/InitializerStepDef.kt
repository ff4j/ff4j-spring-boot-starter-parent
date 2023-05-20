/*-
 * #%L
 * ff4j-spring-services
 * %%
 * Copyright (C) 2013 - 2023 FF4J
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
package org.ff4j.services

import io.cucumber.java8.En
import org.ff4j.core.Feature
import org.ff4j.services.representation.PropertyPojo
import org.ff4j.services.representation.TestAuthorizationsManager

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
class InitializerStepDef : En {

  fun initDataTable() {
    DataTableType { row: Map<String, String> ->
      Feature(
        row["uid"].toString(),
        row["enable"].toString().toBooleanStrict(),
        row["description"].toString(),
        if ("null" != row["group"].toString()) row["group"].toString() else "",
        row["permissions"].toString().split(",")
      )
    }

    DataTableType { row: Map<String, String> ->
      TestAuthorizationsManager(
        row["currentUserPermissions"].toString(), row["allPermissions"].toString()
      )
    }

    DataTableType { row: Map<String, String> ->
      PropertyPojo(
        row["name"].toString(),
        row["description"].toString(),
        row["type"].toString(),
        row["value"].toString(),
        row["fixedValueCSV"].toString()
      )
    }
  }
}
