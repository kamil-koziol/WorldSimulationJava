package com.company.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ObjectRegister<T> {
    private HashMap<String, Class<?>> types;
    public ObjectRegister() {
        types = new HashMap<>();
    }
    public void add(Class<?> tClass) {
        types.put(tClass.getSimpleName(), tClass);
    }
    public Class<?> getClassFromName(String name) {
        return types.get(name);
    }

    public boolean checkClass(Class<?> tClass) {
        boolean positive = types.containsValue(tClass);
        if(!positive) {
            new AccessingNonRegisteredClassException("Trying to access not registered class: " + tClass.getName()).printStackTrace();
        }

        return positive;
    }

    public static class AccessingNonRegisteredClassException extends Exception {
        public AccessingNonRegisteredClassException(String errorMessage) {
            super(errorMessage);
        }
    }

    public Collection<Class<?>> getRegisteredClasses() {
        return types.values();
    }
    public Set<String> getRegisteredNames() {
        return types.keySet();
    }

    public T getInstanceOfName(String name) {
        try {
            return (T) types.get(name).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            new AccessingNonRegisteredClassException("Trying to access not registered class: " + types.get(name).getName()).printStackTrace();
        }

        return null;
    }

    public T getInstanceOfClass(Class<?> tClass) throws AccessingNonRegisteredClassException {
        return getInstanceOfName(tClass.getSimpleName());
    }

    public ArrayList<T> getInstancesOfAllClasses() {
        ArrayList<T> instances = new ArrayList<T>();
        for(Class<?> tClass: getRegisteredClasses()) {
            try {
                instances.add(getInstanceOfClass(tClass));
            } catch (AccessingNonRegisteredClassException e) {
                e.printStackTrace();
            }
        }
        return instances;
    }
}
