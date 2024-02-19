package com.example.foodplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface PlanLocalDataSource {
    Flowable<List<Plan>> getAllPlans();
    void addPlan(Plan plan);
    void removePlan(Plan plan);
    void updatePlan(Plan plan);
    Flowable<Plan> getPlaneByDate(String date);
}
