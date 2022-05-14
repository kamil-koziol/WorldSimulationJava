package com.company;

import com.company.organizmy.Organizm;

import java.util.HashMap;

public class ObjectRegister<T> {
    private HashMap<String, Class<?>> types;
    public ObjectRegister() {
        types = new HashMap<>();
    }

    public void addObjectType(T obj) {
        types.put(obj.getClass().getSimpleName(), obj.getClass());
    }

}
