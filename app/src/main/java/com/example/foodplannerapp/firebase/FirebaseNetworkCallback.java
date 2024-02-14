package com.example.foodplannerapp.firebase;

import android.content.Context;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface FirebaseNetworkCallback {
    public void onSuccessResult(String email, Context context);

    public void onFailureResult(String errorMessage);
}
