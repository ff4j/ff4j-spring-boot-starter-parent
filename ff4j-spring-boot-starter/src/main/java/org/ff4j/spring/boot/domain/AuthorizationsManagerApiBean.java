package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.ff4j.security.AuthorizationsManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class AuthorizationsManagerApiBean implements Serializable {

    private static final long serialVersionUID = 6547399670614500217L;

    @Getter
    private String type = StringUtils.EMPTY;

    @Getter
    private List<String> permissions = new ArrayList<>();

    public AuthorizationsManagerApiBean(AuthorizationsManager authMger) {
        type = authMger.getClass().getCanonicalName();
        permissions = new ArrayList<>(authMger.listAllPermissions());
    }
}
