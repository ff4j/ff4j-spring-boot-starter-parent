package org.ff4j.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "feature store is not cached")
public class FeatureStoreNotCached extends RuntimeException {
    private static final long serialVersionUID = -5715736146662783416L;
}
