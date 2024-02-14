package com.example.foodplannerapp.auth_feature.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.auth_feature.view.LoginView;
import com.example.foodplannerapp.firebase.FirebaseNetworkCallback;
import com.example.foodplannerapp.firebase_repository.FirebaseRepository;
import com.example.foodplannerapp.models.AuthParameters;

public class LoginPresenterImpl implements LoginPresenter, FirebaseNetworkCallback {
   private FirebaseRepository repository;
   private LoginView view;
   private static LoginPresenterImpl instance = null;

   private LoginPresenterImpl(FirebaseRepository repository,LoginView view)
   {
       this.repository = repository;
       this.view = view;
   }
   public static synchronized LoginPresenterImpl getInstance(FirebaseRepository repository,LoginView view)
   {
       if (instance == null) {
           instance = new LoginPresenterImpl(repository,view);
       }else {
           instance.view = view;
       }
       return instance;
   }
    @Override
    public void loginUserWithEmailAndPassword(AuthParameters parameters) {
        repository.loginUserWithEmailAndPassword(parameters,this);
    }

    @Override
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
