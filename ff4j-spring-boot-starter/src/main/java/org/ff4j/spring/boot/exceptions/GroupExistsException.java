package org.ff4j.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "group already exists")
public class GroupExistsException extends RuntimeException {
    private static final long serialVersionUID = 8560277437073933250L;
}
