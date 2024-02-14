package com.example.foodplannerapp.models;

import android.app.Activity;

import com.example.foodplannerapp.firebase.FirebaseNetworkCallback;

public class AuthParameters {
    public AuthParameters(String email, String password, Activity context) {
        this.email = email;
        this.password = password;
        this.context = context;
    }

    public String email;
   public String password;

   public FirebaseNetworkCallback networkCallback;
   public Activity context;
}
