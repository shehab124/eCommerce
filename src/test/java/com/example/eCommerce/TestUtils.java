package com.example.eCommerce;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObject(Object target, String fieldName, Object toInject)
    {
        boolean wasPrivate = false;

        try {
            Field field = target.getClass().getDeclaredField(fieldName);

            if(!field.isAccessible())
            {
                field.setAccessible(true);
                wasPrivate = true;
            }
            field.set(target, toInject);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
