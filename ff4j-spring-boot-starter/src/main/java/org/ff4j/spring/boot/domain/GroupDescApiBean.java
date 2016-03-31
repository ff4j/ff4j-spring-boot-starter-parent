package org.ff4j.spring.boot.domain;


import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class GroupDescApiBean implements Serializable {

    private static final long serialVersionUID = -7339190302097692175L;

    @Getter
    private String groupName;

    @Getter
    private List<String> features = new ArrayList<>();

    public GroupDescApiBean(String groupName, List<String> names) {
        this.groupName = groupName;
        this.features = names;
    }
}
