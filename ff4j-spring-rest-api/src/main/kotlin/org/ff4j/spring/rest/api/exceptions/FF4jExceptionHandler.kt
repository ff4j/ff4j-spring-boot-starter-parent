/*-
 * #%L
 * ff4j-spring-rest-api
 * %%
 * Copyright (C) 2013 - 2026 FF4J
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

package org.ff4j.spring.rest.api.exceptions

import org.ff4j.exception.InvalidPropertyTypeException
import org.ff4j.services.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

/**
 * @author [Paul Williams](mailto:paul58914080@gmail.com)
 */
@ControllerAdvice(basePackages = ["org.ff4j.spring.rest.api.resources"])
class FF4jExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(value = [(IllegalArgumentException::class)])
  fun badRequestHandler(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "bad request"))

  @ExceptionHandler(value = [(FeatureNotFoundException::class)])
  fun featureNotFoundException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "feature not found"))

  @ExceptionHandler(value = [(FeatureIdBlankException::class)])
  fun featureIdBlankException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "feature uid cannot be blank"))

  @ExceptionHandler(value = [(FeatureIdNotMatchException::class)])
  fun featureIdNotMatchException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.BAD_REQUEST,
          "feature uid did not match with the requested feature uid to be created or updated"
        )
      )

  @ExceptionHandler(value = [(FlippingStrategyBadRequestException::class)])
  fun flippingStrategyBadRequestException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.BAD_REQUEST,
          "flipping strategy specified wrongly"
        )
      )

  @ExceptionHandler(value = [(PropertiesBadRequestException::class)])
  fun propertiesBadRequestException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.BAD_REQUEST,
          "properties specified wrongly"
        )
      )

  @ExceptionHandler(value = [(RoleExistsException::class)])
  fun roleExistsException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_MODIFIED)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.NOT_MODIFIED,
          "role already exists"
        )
      )

  @ExceptionHandler(value = [(RoleNotExistsException::class)])
  fun roleNotExistsException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.NOT_FOUND,
          "role does not exist"
        )
      )

  @ExceptionHandler(value = [(GroupExistsException::class)])
  fun groupExistsException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_MODIFIED)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.NOT_MODIFIED,
          "group already exists"
        )
      )

  @ExceptionHandler(value = [(GroupNotExistsException::class)])
  fun groupNotExistsException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND,
        "group does not exist"
      )
    )

  @ExceptionHandler(value = [(FeatureStoreNotCached::class)])
  fun featureStoreNotCached(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND,
        "feature store is not cached"
      )
    )

  @ExceptionHandler(value = [(AuthorizationNotExistsException::class)])
  fun authorizationNotExistsException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND,
        "no security has been defined"
      )
    )

  @ExceptionHandler(value = [(PropertyNotFoundException::class)])
  fun propertyNotFoundException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND,
        "property not found"
      )
    )

  @ExceptionHandler(value = [(PropertyNameBlankException::class)])
  fun propertyNameBlankException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        "property name cannot be blank"
      )
    )

  @ExceptionHandler(value = [(InvalidPropertyTypeException::class)])
  fun propertyValueInvalidException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST, "bad request"
      )
    )

  @ExceptionHandler(value = [(PropertyNameNotMatchException::class)])
  fun propertyNameNotMatchException(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(
        ProblemDetail.forStatusAndDetail(
          HttpStatus.BAD_REQUEST,
          "property name did not match with the requested property name to be created or updated"
        )
      )

  @ExceptionHandler(value = [(PropertyStoreNotCached::class)])
  fun propertyStoreNotCached(): ResponseEntity<ProblemDetail> =
    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND, "property store is not cached"
      )
    )
}
