package com.example.foodplannerapp.auth_feature.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.database.FavouritesLocalDataSource;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepository;

public class ProfilePresenterImpl implements ProfilePresenter{
   private FirebaseAuthRepository repository;
   private FavouritesLocalDataSource localDataSource;

   private static ProfilePresenterImpl instance = null;
   private ProfilePresenterImpl(FirebaseAuthRepository repository,
                                FavouritesLocalDataSource localDataSource)
   {
       this.repository = repository;
       this.localDataSource = localDataSource;
   }

   public static ProfilePresenterImpl getInstance(FirebaseAuthRepository repository,
                                                  FavouritesLocalDataSource localDataSource)
   {
       if (instance == null) {
           instance = new ProfilePresenterImpl(repository,localDataSource);
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
