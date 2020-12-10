package main.java.com.codepath.techscreens.dydx;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class TechScreen {
    private final static String QUOTE = "\"";
    private final static String OPEN_BRACKET = "{";
    private final static String CLOSE_BRACKET = "}";
    private final static String OPEN_ARRAY_BRACKET = "[";
    private final static String CLOSE_ARRAY_BRACKET = "]";
    private final static String COLON = ":";
    private final static String COMMA = ",";
    private final static Set<String> protectedTypes = new HashSet<>(Arrays.asList("String", "Integer", "Double", "Float"));

    /**
     * This tech screen was from DyDx.
     *
     * Given an object, serialize it into json format. This is best done with a dynamic
     * language like Python or JavaScript.
     *
     * This problem isn't that complicated, but to do this in Java, requires knowing the
     * Reflection API's in java, which I wasn't familiar with at the time.
     *
     * At any rate, I knew the correct algorithm to use. Essentially one had to use reflection
     * to get the fields of a java object, and then perform a DFS on every field.
     *
     * The problem only becomes complicated when you try to serialize it into JSON format
     * for different field Types, like String, int, array, or nested objects.
     * @param object object we Wish to serialize
     * @return json string
     */
    public String getJsonString(Object object) {
        StringBuffer sb = new StringBuffer();
        sb.append(OPEN_BRACKET);
        sb.append(getJsonStringHelper(object, null, false));
        sb.append(CLOSE_BRACKET);
        return sb.toString();
    }

    public String getJsonStringHelper(Object object, String name, boolean isArray) {
        StringBuffer sb = new StringBuffer();
        if (isArray) {
            sb.append(getNameFromField(name));
            sb.append(OPEN_ARRAY_BRACKET);
            int length = Array.getLength(object);
            String prefix = "";
            for (int i = 0; i < length; i ++) {
                sb.append(prefix);
                prefix = ",";
                Object arrayElement = Array.get(object, i);
                sb.append(getJsonStringHelper(arrayElement,
                        arrayElement.getClass().getSimpleName(),
                        arrayElement.getClass().isArray())
                );
            }
            sb.append(CLOSE_ARRAY_BRACKET);
            return sb.toString();
        } else {
            if (name != null && !protectedTypes.contains(name)) {
                sb.append(getNameFromField(name));
                sb.append(OPEN_BRACKET);
            }
        }

        Field[] fields = object.getClass().getDeclaredFields();
        String prefix = "";
        for (Field field : fields) {
            Object nested = getFieldValue(field, object);
            if (nested != null) {
                sb.append(prefix);
                prefix = COMMA;
                if (field.getType().isArray()) {
                    sb.append(getJsonStringHelper(nested, field.getName(), field.getType().isArray()));
                } else if (field.getType().equals(Person.class)) {
                    sb.append(getJsonStringHelper(nested, field.getName(), field.getType().isArray()));
                } else if (field.getType().equals(String.class)) {
                    sb.append(getStringFromField(field, object));
                } else if (field.getType().equals(Integer.class)) {
                    sb.append(getStringFromField(field, object));
                }
            } else {
                if (field.getType().getName().equals("[C")) {
                    sb.append(QUOTE);
                    sb.append(object);
                    sb.append(QUOTE);
                }
            }
        }

        if (name != null && !protectedTypes.contains(name)) {
            sb.append(CLOSE_BRACKET);
        }

        return sb.toString();
    }

    public String getStringFromField(Field field, Object object) {
        StringBuffer sb = new StringBuffer();
        sb.append(QUOTE);
        sb.append(field.getName());
        sb.append(QUOTE);
        sb.append(COLON);
        sb.append(QUOTE);
        sb.append(getFieldValue(field, object));
        sb.append(QUOTE);
        return sb.toString();
    }

    public String getNameFromField(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(QUOTE);
        sb.append(name);
        sb.append(QUOTE);
        sb.append(COLON);
        return sb.toString();
    }

    public Object getFieldValue(Field field, Object object) {
        try {
            return field.get(object);
        } catch (Exception ex) {
            // error
        }
        return null;
    }
}
