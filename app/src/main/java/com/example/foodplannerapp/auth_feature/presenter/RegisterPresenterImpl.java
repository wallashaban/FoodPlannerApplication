package com.example.foodplannerapp.auth_feature.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.auth_feature.view.RegisterView;
import com.example.foodplannerapp.firebase.FirebaseNetworkCallback;
import com.example.foodplannerapp.firebase_repository.FirebaseRepository;
import com.example.foodplannerapp.models.AuthParameters;

public class RegisterPresenterImpl implements RegisterPresenter,
        FirebaseNetworkCallback {
    private FirebaseRepository repository;;
    private RegisterView view;
    private static RegisterPresenterImpl instance = null;
    private RegisterPresenterImpl(FirebaseRepository repository,RegisterView view)
    {
        this.repository = repository;
        this.view = view;
    }
    public static RegisterPresenterImpl getInstance(FirebaseRepository repository,
                                                    RegisterView view)
    {
        if (instance == null) {
            instance =new RegisterPresenterImpl(repository,view);
        }
        return instance;
    }

    @Override
    public void registerUserByEmailAndPassword(AuthParameters parameters) {
        repository.registerUserWithEmailAndPassword(parameters,this);
    }


    public void onSuccessResult(String email, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        view.showData();
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }
}
