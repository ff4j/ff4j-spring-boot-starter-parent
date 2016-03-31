/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */

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
