package com.example.foodplannerapp.firebase;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface FirebasePlanNetworkCallBack {
    public void onSuccessResult(List<Plan> plans);
    public void onErrorResult(String errorMessage);
}
