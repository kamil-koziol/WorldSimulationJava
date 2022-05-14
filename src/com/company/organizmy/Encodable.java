package com.company.organizmy;


import java.util.ArrayList;

public interface Encodable {
    public ArrayList<String> getParameters();
    public String encodeToString(String delimeter);
    public void decodeFromString(String str, String delimeter);
}
