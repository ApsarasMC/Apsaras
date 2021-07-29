package org.apsarasmc.plugin.config;

import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.FieldProperty;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ApsarasPropertyUtils extends PropertyUtils {
    public ApsarasPropertyUtils(){
        setBeanAccess(BeanAccess.FIELD);
    }

    @Override
    protected Set<Property> createPropertySet(Class<? extends Object> type, BeanAccess bAccess) {
        Collection<Property> props = new LinkedHashSet<>();
        Set<String> nameSet = new HashSet<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)
                        && !nameSet.contains(field.getName())) {
                    nameSet.add(field.getName());
                    props.add(new FieldProperty(field));
                }
            }
        }

        Set<Property> properties = new LinkedHashSet<>();
        for (Property property : props) {
            if (property.isReadable() && (isAllowReadOnlyProperties() || property.isWritable())) {
                properties.add(property);
            }
        }
        return properties;
    }
}
