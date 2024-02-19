package com.example.foodplannerapp.auth_feature.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.foodplannerapp.auth_feature.view.LoginView;
import com.example.foodplannerapp.firebase.FirebaseAuthNetworkCallback;
import com.example.foodplannerapp.firebase.FirebaseMealsNetwokCallBack;
import com.example.foodplannerapp.firebase.FirebasePlanNetworkCallBack;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepository;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public class LoginPresenterImpl implements LoginPresenter,
        FirebaseAuthNetworkCallback,
        FirebasePlanNetworkCallBack,
        FirebaseMealsNetwokCallBack {
    private FirebaseAuthRepository repository;
    private LoginView view;
    private static LoginPresenterImpl instance = null;

    private LoginPresenterImpl(FirebaseAuthRepository repository, LoginView view) {
        this.repository = repository;
        this.view = view;
    }

    public static synchronized LoginPresenterImpl getInstance(FirebaseAuthRepository repository, LoginView view) {
        if (instance == null) {
            instance = new LoginPresenterImpl(repository, view);
        } else {
            instance.view = view;
        }
        return instance;
    }

    @Override
    public void loginUserWithEmailAndPassword(AuthParameters parameters) {
        repository.loginUserWithEmailAndPassword(parameters, this);
    }

    @Override
    public void onSuccessResult(String email, String name, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.commit();
        repository.getFavMealsFromFirebase(this);
        repository.getPlansFromFirebase(this);
        Log.i("TAG", "onSuccessResult: Email" + sharedPreferences.getString("email", null));
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }

    @Override
    public void onMealsSuccessResult(List<Meal> meal) {
        view.showMealsData(meal);
    }

    @Override
    public void onMealsErrorResult(String errorMessage) {
        view.showMealsErrorMessage(errorMessage);
    }

    @Override
    public void onPlansSuccessResult(List<Plan> plans) {
        view.showPlansData(plans);
    }

    @Override
    public void onPlansErrorResult(String errorMessage) {
        view.showPlansErrorMessage(errorMessage);
    }
}
