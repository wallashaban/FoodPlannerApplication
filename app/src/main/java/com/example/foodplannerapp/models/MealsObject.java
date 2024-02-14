package com.example.foodplannerapp.models;

import java.util.ArrayList;
import java.util.List;

public class MealsObject {
    public List<Meal> getMeals() {
        return meals;
    }

    public MealsObject() {
        meals = new ArrayList<>();
    }

    private List<Meal> meals;

}
