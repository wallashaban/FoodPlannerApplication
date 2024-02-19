package com.example.foodplannerapp.auth_feature.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.database.LocalDataSource;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepository;

public class ProfilePresenterImpl implements ProfilePresenter {
    private FirebaseAuthRepository repository;
    private LocalDataSource localDataSource;

    private static ProfilePresenterImpl instance = null;

    private ProfilePresenterImpl(FirebaseAuthRepository repository,
                                 LocalDataSource localDataSource) {
        this.repository = repository;
        this.localDataSource = localDataSource;
    }

    public static synchronized ProfilePresenterImpl getInstance(FirebaseAuthRepository repository,
                                                   LocalDataSource localDataSource) {
        if (instance == null) {
            instance = new ProfilePresenterImpl(repository, localDataSource);
        }
        return instance;
    }

    @Override
    public void logOut(Context context) {
        repository.logOut();
        localDataSource.deleteAllFavMeals();
        localDataSource.deleteAllPlans();
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", null);
        editor.commit();
    }
}
