package com.example.foodplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

@Dao
public interface PlanDAO {
    @Query("SELECT * FROM " + Constants.PLAN_TABLE)
    LiveData<List<Plan>> getAllPlans();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addPlan(Plan plan);
    @Delete
    void removePlan(Plan plan);
    @Update
    void updatePlan(Plan plan);
    @Query("SELECT * FROM "+Constants.PLAN_TABLE+" WHERE date = :date")
    LiveData<Plan> getPlaneByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPlans(List<Plan> plans);
    @Query("DELETE FROM "+Constants.PLAN_TABLE)
    void deleteAllPlans();
}
