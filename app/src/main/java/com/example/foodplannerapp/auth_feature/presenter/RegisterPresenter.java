package com.example.foodplannerapp.auth_feature.presenter;

import com.example.foodplannerapp.models.AuthParameters;

public interface RegisterPresenter {
    public void registerUserByEmailAndPassword(AuthParameters parameters);
}
