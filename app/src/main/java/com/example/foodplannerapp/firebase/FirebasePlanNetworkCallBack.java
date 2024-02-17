package com.example.foodplannerapp.firebase;

import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface FirebasePlanNetworkCallBack {
    public void onPlansSuccessResult(List<Plan> plans);
    public void onPlansErrorResult(String errorMessage);
}
