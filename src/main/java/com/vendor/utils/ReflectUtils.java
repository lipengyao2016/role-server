package com.vendor.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectUtils {

    public static void setField(Object obj,String fieldName,Object value) throws NoSuchFieldException, IllegalAccessException {
        Class cls = obj.getClass();
        Field f = null;
        f = cls.getDeclaredField(fieldName);
        System.out.println(f);
        f.setAccessible(true);
        f.set(obj, value);
    }

    public static Object  createInstance(Class cls) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor con = cls.getConstructor(null);
        return con.newInstance();
    }

    public static Object getField(Object obj,String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class cls = obj.getClass();
        Field f = null;
        f = cls.getDeclaredField(fieldName);
        System.out.println(f);
        f.setAccessible(true);
        return f.get(obj);
    }
}
