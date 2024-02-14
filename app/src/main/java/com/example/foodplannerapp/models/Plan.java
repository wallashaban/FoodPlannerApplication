package com.example.foodplannerapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.foodplannerapp.Shared.Constants;

import java.util.List;



@Entity(tableName = Constants.PLAN_TABLE)
@TypeConverters(MealPlanConverter.class)
public class Plan {
    public Plan(@NonNull String date, Meal meal) {
        Date = date;
        this.meal = meal;
    }

    public Plan() {
    }

    public void setDate(String day) {
        this.Date = day;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getDate() {
        return Date;
    }

    public Meal getMeal() {
        return meal;
    }

    @PrimaryKey
    @NonNull
    private String  Date;
   private Meal meal;

    @Override
    public String toString() {
        return "Plan{" +
                "day=" + Date +
                ", meals=" + meal +
                '}';
    }
}
