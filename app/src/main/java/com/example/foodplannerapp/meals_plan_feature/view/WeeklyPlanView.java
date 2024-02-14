package com.example.foodplannerapp.meals_plan_feature.view;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface WeeklyPlanView {
    public void setPlansList(LiveData<List<Plan>> plans);
}
