package com.example.foodplannerapp.meals_plan_feature.view;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface WeeklyPlanView {
    public void setPlansList(Flowable<List<Plan>> plans);
}
