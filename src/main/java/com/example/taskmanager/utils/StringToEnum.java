package com.example.taskmanager.utils;

public class StringToEnum {
    public static <E extends Enum<E>> E convertStringToEnum(Class<E> enumClass, String value) {
        try {
            // Use Enum.valueOf to convert the string to the enum
            return (E) Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            // Handle invalid enum constants
            System.err.println("Error: " + value + " is not a valid constant of " + enumClass.getSimpleName());
            return null; // or handle error as needed
        }
    }
}
