package com.example.foodplannerapp.models;

import android.app.Activity;

import com.example.foodplannerapp.firebase.FirebaseAuthNetworkCallback;

public class AuthParameters {
    public AuthParameters(String email, String password, String name, Activity context) {
        this.email = email;
        this.password = password;
        this.context = context;
        this.name = name;
    }

    public String email;
    public String password;
    public String name;

    public FirebaseAuthNetworkCallback networkCallback;
    public Activity context;
}
