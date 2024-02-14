package com.example.foodplannerapp.auth_feature.presenter;

import com.example.foodplannerapp.firebase.FirebaseNetworkCallback;
import com.example.foodplannerapp.models.AuthParameters;

public interface LoginPresenter {
    public void loginUserWithEmailAndPassword(AuthParameters parameters);
}
