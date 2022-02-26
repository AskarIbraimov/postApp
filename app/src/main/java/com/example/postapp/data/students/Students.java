package com.example.postapp.data.students;

import java.util.HashMap;
import java.util.Map;

public class Students {

    private final static Map<Integer, String> students = new HashMap<>();

    public static Map<Integer, String> getStudents() {
        students.put(12, "Аскар");
        students.put(0, "default");
        return students;
    }
}
