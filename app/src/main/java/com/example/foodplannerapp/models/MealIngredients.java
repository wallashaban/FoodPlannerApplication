package com.example.foodplannerapp.models;

public class MealIngredients {
    private String name;
    private String measurement;

    @Override
    public String toString() {
        return "MealIngredients{" +
                "name='" + name + '\'' +
                ", measurement='" + measurement + '\'' +
                ", image=" + image +
                '}';
    }

    public int getImage() {
        return image;
    }

    private int image;

    public MealIngredients(String name, String measurement) {
        this.name = name;
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public String getMeasurement() {
        return measurement;
    }
}
