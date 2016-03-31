package org.ff4j.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "property name did not match with the requested property name to be created or updated")
public class PropertyNameNotMatchException extends RuntimeException {
    private static final long serialVersionUID = -1152106816628162171L;
}
