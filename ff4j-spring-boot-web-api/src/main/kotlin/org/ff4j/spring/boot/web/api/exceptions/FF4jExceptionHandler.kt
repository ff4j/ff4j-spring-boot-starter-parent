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

package org.ff4j.spring.boot.web.api.exceptions

import org.ff4j.exception.InvalidPropertyTypeException
import org.ff4j.services.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by Paul
 *
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@ControllerAdvice(basePackages = ["org.ff4j.spring.boot.web.api.resources"])
class FF4jExceptionHandler {

  @ExceptionHandler(value = [(IllegalArgumentException::class)])
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
  fun badRequestHandler() { // no-op comment, do nothing
  }

  @ExceptionHandler(value = [(FeatureNotFoundException::class)])
  fun featureNotFoundException(): ResponseEntity<String> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body("feature not found")

  @ExceptionHandler(value = [(FeatureIdBlankException::class)])
  fun featureIdBlankException() =
    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("feature uid cannot be blank")

  @ExceptionHandler(value = [(FeatureIdNotMatchException::class)])
  fun featureIdNotMatchException() =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body("feature uid did not match with the requested feature uid to be created or updated")

  @ExceptionHandler(value = [(FlippingStrategyBadRequestException::class)])
  fun flippingStrategyBadRequestException() =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body("flipping strategy specified wrongly")

  @ExceptionHandler(value = [(PropertiesBadRequestException::class)])
  fun propertiesBadRequestException() =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body("properties specified wrongly")

  @ExceptionHandler(value = [(RoleExistsException::class)])
  fun roleExistsException() =
    ResponseEntity.status(HttpStatus.NOT_MODIFIED)
      .body("role already exists")

  @ExceptionHandler(value = [(RoleNotExistsException::class)])
  fun roleNotExistsException() =
    ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body("role does not exist")

  @ExceptionHandler(value = [(GroupExistsException::class)])
  fun groupExistsException() =
    ResponseEntity.status(HttpStatus.NOT_MODIFIED)
      .body("group already exists")

  @ExceptionHandler(value = [(GroupNotExistsException::class)])
  fun groupNotExistsException(): ResponseEntity<String> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body("group does not exist")

  @ExceptionHandler(value = [(FeatureStoreNotCached::class)])
  fun featureStoreNotCached() =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body("feature store is not cached")

  @ExceptionHandler(value = [(AuthorizationNotExistsException::class)])
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no security has been defined")
  fun authorizationNotExistsException() { // no-op comment, do nothing
  }

  @ExceptionHandler(value = [(PropertyNotFoundException::class)])
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "property not found")
  fun propertyNotFoundException() { // no-op comment, do nothing
  }

  @ExceptionHandler(value = [(PropertyNameBlankException::class)])
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "property name cannot be blank")
  fun propertyNameBlankException() { // no-op comment, do nothing
  }

  @ExceptionHandler(value = [(InvalidPropertyTypeException::class)])
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
  fun propertyValueInvalidException() { // no-op comment, do nothing
  }

  @ExceptionHandler(value = [(PropertyNameNotMatchException::class)])
  @ResponseStatus(
    value = HttpStatus.BAD_REQUEST,
    reason = "property name did not match with the requested property name to be created or updated"
  )
  fun propertyNameNotMatchException() { // no-op comment, do nothing
  }

  @ExceptionHandler(value = [(PropertyStoreNotCached::class)])
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "property store is not cached")
  fun propertyStoreNotCached() { // no-op comment, do nothing
  }
}
