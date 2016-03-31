package org.ff4j.spring.boot.domain;


import lombok.Getter;
import lombok.Setter;
import org.ff4j.property.Property;
import org.ff4j.property.util.PropertyFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class PropertyApiBean implements Serializable {

    private static final long serialVersionUID = -5366099799518640405L;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String value;

    @Getter
    private Set<String> fixedValues = new HashSet<>();

    public PropertyApiBean() {
        super();
    }

    public PropertyApiBean(Property<?> property) {
        if (property != null) {
            this.name = property.getName();
            this.description = property.getDescription();
            this.type = property.getType();
            this.value = property.asString();
            if (property.getFixedValues() != null) {
                fixedValues.addAll(property.getFixedValues().stream().map(Object::toString).collect(Collectors.toList()));
            }
        }
    }

    public Property asProperty() {
        return PropertyFactory.createProperty(name, type, value, description, fixedValues);
    }
}
