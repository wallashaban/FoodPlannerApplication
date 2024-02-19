package com.example.foodplannerapp.firebase_repository;

import com.example.foodplannerapp.firebase.FirebaseAuthNetworkCallback;
import com.example.foodplannerapp.firebase.FirebaseMealsNetwokCallBack;
import com.example.foodplannerapp.firebase.FirebasePlanNetworkCallBack;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSource;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public class FirebaseAuthRepositoryImpl implements FirebaseAuthRepository {
    private FirebaseRemoteDataSource dataSource;
    private static FirebaseAuthRepositoryImpl instance = null;

    private FirebaseAuthRepositoryImpl(FirebaseRemoteDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static synchronized FirebaseAuthRepositoryImpl getInstance(FirebaseRemoteDataSource
                                                                              dataSource) {
        if (instance == null) {
            instance = new FirebaseAuthRepositoryImpl(dataSource);
        }
        return instance;
    }

    @Override
    public void registerUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback) {
        dataSource.registerUserWithEmailAndPassword(parameters, networkCallback);
    }

    @Override
    public void loginUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback) {
        dataSource.loginUserWithEmailAndPassword(parameters, networkCallback);
    }

    @Override
    public void getFavMealsFromFirebase(FirebaseMealsNetwokCallBack netwokCallBack) {
        dataSource.getAllFavMealFromFirebase(netwokCallBack);
    }

    @Override
    public void getPlansFromFirebase(FirebasePlanNetworkCallBack networkCallBack) {
        dataSource.getAllPlansFromFirebase(networkCallBack);
    }

    @Override
    public void logOut() {
        dataSource.logOut();
    }


}
