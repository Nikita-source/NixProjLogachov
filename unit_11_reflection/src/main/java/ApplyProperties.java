import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.lang.reflect.Field;

public class ApplyProperties {


    public <T> T createPropertyObject(Class<T> propertyClass) {
        try {
            LoadProperties loadProperties = new LoadProperties();
            Properties props = loadProperties.getProperties();
            T instance = propertyClass.getDeclaredConstructor().newInstance();

            for (Field field : propertyClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(PropertyKey.class)) {
                    continue;
                }

                PropertyKey propertyKey = field.getAnnotation(PropertyKey.class);

                if (!props.containsKey(propertyKey.value())) {
                    continue;
                }

                var type = field.getType();
                String value = props.getProperty(propertyKey.value());

                if (type == String.class) {
                    field.set(instance, value);
                } else if (type == int.class || type == Integer.class) {
                    field.set(instance, Integer.parseInt(value));
                } else if (type == boolean.class || type == Boolean.class) {
                    field.set(instance, Boolean.parseBoolean(value));
                } else if (type == char.class || type == Character.class) {
                    field.set(instance, value.charAt(0));
                } else if (type == long.class || type == Long.class) {
                    field.set(instance, Long.parseLong(value));
                } else if (type == float.class || type == Float.class) {
                    field.set(instance, Float.parseFloat(value));
                } else if (type == double.class || type == Double.class) {
                    field.set(instance, Double.parseDouble(value));
                } else if (type.isEnum()) {
                    Enum en = Enum.valueOf((Class<Enum>) type, value);
                    field.set(instance, en);
                } else {
                    throw new RuntimeException("Property error");
                }
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
