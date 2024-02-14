package com.example.foodplannerapp.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.models.AuthParameters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseRemoteDataSourceImpl implements FirebaseRemoteDataSource{
    private static FirebaseRemoteDataSourceImpl instance = null;
    FirebaseAuth auth;
    private FirebaseRemoteDataSourceImpl(){
        auth =  FirebaseAuth.getInstance();
    }
    public static synchronized FirebaseRemoteDataSourceImpl getInstance()
    {
        if (instance == null) {
            instance = new FirebaseRemoteDataSourceImpl();
        }
        return instance;
    }

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void registerUserWithEmailAndPassword(AuthParameters parameters,FirebaseNetworkCallback networkCallback) {
        auth.createUserWithEmailAndPassword(parameters.email, parameters.password)
                .addOnCompleteListener(parameters.context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            networkCallback.onFailureResult(task.getException().toString());
                            //Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                              //      Toast.LENGTH_LONG).show();
                            Log.e("MyTag", task.getException().toString());
                        } else {

                            networkCallback.onSuccessResult(parameters.email, parameters.context);
                            //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            //finish();
                        }
                    }
                });

    }

    @Override
    public void loginUserWithEmailAndPassword(AuthParameters parameters,FirebaseNetworkCallback networkCallback) {
        auth.signInWithEmailAndPassword(parameters.email, parameters.password)
                .addOnCompleteListener(parameters.context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
//                            Toast.makeText(parameters.context, "Authentication failed." + task.getException(),
//                                    Toast.LENGTH_LONG).show();
//                            Log.e("MyTag", task.getException().toString());
                            networkCallback.onFailureResult(task.getException().toString());

                        } else {
                            networkCallback.onSuccessResult(parameters.email,parameters.context);
//                            Log.i("TAG", "onComplete: USer :"+ auth.getCurrentUser().getEmail());
//
//                            Log.i("TAG", "onComplete: Login");
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    }
                });

    }

    @Override
    public void addMealToFavourite(Meal meal) {

    }

    @Override
    public void removeMealFromFavourite(Meal meal) {

    }

    @Override
    public void addMealToPlan(Plan plan) {

    }

    @Override
    public void removeMealFromPlan(Plan plan) {

    }
}
