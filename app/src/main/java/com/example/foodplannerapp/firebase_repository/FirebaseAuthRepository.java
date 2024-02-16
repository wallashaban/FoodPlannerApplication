package com.example.foodplannerapp.firebase_repository;

import com.example.foodplannerapp.firebase.FirebaseAuthNetworkCallback;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public interface FirebaseAuthRepository {
    public void registerUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback);
    public void loginUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback);

    public void logOut();
}
