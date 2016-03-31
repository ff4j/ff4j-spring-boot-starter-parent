package org.ff4j.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "feature uid did not match with the requested feature uid to be created or updated")
public class FeatureIdNotMatchException extends RuntimeException {
    private static final long serialVersionUID = -2878761447146656187L;
}
