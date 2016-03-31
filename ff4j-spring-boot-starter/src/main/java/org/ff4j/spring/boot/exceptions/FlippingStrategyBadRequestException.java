package org.ff4j.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "flipping strategy specified wrongly")
public class FlippingStrategyBadRequestException extends RuntimeException {
    private static final long serialVersionUID = -3239022865148294488L;
}
