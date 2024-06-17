package org.example.service;

public class Utils {

    public static Integer getDist(Integer problem, Integer id1, Integer id2) {

        if (problem == 1) {
            return Math.abs(id1 / 5 - id2 / 5) + Math.abs(id1 % 5 - id2 % 5);
        } else {
            return Math.abs(id1 / 60 - id2 / 60) + Math.abs(id1 % 60 - id2 % 60);
        }
    }
}
