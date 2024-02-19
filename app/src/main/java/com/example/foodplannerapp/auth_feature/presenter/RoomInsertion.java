package com.example.foodplannerapp.auth_feature.presenter;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface RoomInsertion {
    public void AddAllFavourites(List<Meal> meals);

    public void AddAllPlans(List<Plan> plans);
}
