package com.example.foodplannerapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.foodplannerapp.Shared.Constants;

@Entity(tableName = Constants.DAILY_MEAL_TABLE)
@TypeConverters(MealPlanConverter.class)
public class DialyMeal {
    @PrimaryKey
            @NonNull
    private String date;

    @NonNull
    public String getDate() {
        return date;
    }

    public Meal getMeal() {
        return meal;
    }

    private Meal meal;

    public DialyMeal(@NonNull String date, Meal meal) {
        this.date = date;
        this.meal = meal;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
