package com.example.foodplannerapp.firebase;

import android.content.Context;

public interface FirebaseAuthNetworkCallback {
    public void onSuccessResult(String email, String name, Context context);

    public void onFailureResult(String errorMessage);
}
