package com.example.foodplannerapp.auth_feature.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepository;

public class ProfilePresenterImpl implements ProfilePresenter{
   private FirebaseAuthRepository repository;

   private static ProfilePresenterImpl instance = null;
   private ProfilePresenterImpl(FirebaseAuthRepository repository)
   {
       this.repository = repository;
   }

   public static ProfilePresenterImpl getInstance(FirebaseAuthRepository repository)
   {
       if (instance == null) {
           instance = new ProfilePresenterImpl(repository);
       }
       return instance;
   }
    @Override
    public void logOut(Context context) {
        repository.logOut();
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", null);
        editor.commit();
    }
}
