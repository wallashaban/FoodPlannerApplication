package com.example.foodplannerapp.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.models.AuthParameters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRemoteDataSourceImpl implements FirebaseRemoteDataSource{
    private static FirebaseRemoteDataSourceImpl instance = null;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore ;
    private final String TAG = "FirebaseDataSource";
    private  SharedPreferences sharedPreferences;
    private String email;
    private FirebaseRemoteDataSourceImpl(Context context){
        auth =  FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email",null);
    }
    public static synchronized FirebaseRemoteDataSourceImpl getInstance(Context context)
    {
        if (instance == null) {
            instance = new FirebaseRemoteDataSourceImpl(context);
        }
        return instance;
    }



    @Override
    public void registerUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback) {
        auth.createUserWithEmailAndPassword(parameters.email, parameters.password)
                .addOnCompleteListener(parameters.context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            networkCallback.onFailureResult(task.getException().toString());
                            Log.e("MyTag", task.getException().toString());
                        } else {

                            networkCallback.onSuccessResult(parameters.email,parameters.name, parameters.context);
                        }
                    }
                });

    }

    @Override
    public void loginUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback) {
        auth.signInWithEmailAndPassword(parameters.email, parameters.password)
                .addOnCompleteListener(parameters.context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            networkCallback.onFailureResult(task.getException().toString());

                        } else {
                            networkCallback.onSuccessResult(parameters.email,parameters.name,parameters.context);
                      }
                    }
                });

    }

    @Override
    public void addMealToFavourite(Meal meal) {
        Map<String,String> data = new HashMap<>();
        data.put("email",email);
        data.put("mealId",meal.getMealId());
        data.put("mealName",meal.getMealName());
        data.put("mealThumb",meal.getMealThumb());
        firebaseFirestore.collection("favourites").document(email+meal.getMealId()).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "Meal Added successfully");
                    }
                });

    }

    @Override
    public void removeMealFromFavourite(Meal meal) {
        firebaseFirestore.collection("favourites").document(email+meal.getMealId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "meal deleted successfully");
                    }
                });
    }

    @Override
    public void addMealToPlan(Plan plan) {
        Map<String,Object> data = new HashMap<>();
        data.put("email",email);
        data.put("date",plan.getDate());
        data.put("meal",plan.getMeal());
        firebaseFirestore.collection("plan").document(email+plan.getDate().replace("/"," "))
                .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "Plan added successfully");
                    }
                });
    }

    @Override
    public void removeMealFromPlan(Plan plan) {
        firebaseFirestore.collection("plan").document(email+plan.getDate().replace("/"," "))
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "Plan deleted successfully");
                    }
                });
    }

    @Override
    public void getAllPlansFromFirebase(FirebasePlanNetworkCallBack networkCallBack) {
        List<Plan> plans = new ArrayList<>();

        firebaseFirestore.collection("plan").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc: task.getResult())
                    {

                        if(doc.get("email").equals(sharedPreferences.getString("email",null))) {
                            Plan plan = doc.toObject(Plan.class);
                            plans.add(plan);
                            Log.i(TAG, "firebase: plans " + plan.toString() + " doc " + doc.get("meal"));
                        }
                        Log.i(TAG, "onComplete: email plan"+sharedPreferences.getString("email",null));
                    }
                    networkCallBack.onSuccessResult(plans);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                networkCallBack.onErrorResult(e.getMessage());
            }
        });
    }

    @Override
    public void getAllFavMealFromFirebase(FirebaseMealsNetwokCallBack netwokCallBack) {
        List<Meal> meals = new ArrayList<>();

        firebaseFirestore.collection("favourites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc: task.getResult())
                    {

                        if(doc.get("email").equals(sharedPreferences.getString("email",null))) {
                            Meal meal = doc.toObject(Meal.class);
                            meals.add(meal);
                            Log.i(TAG, "firebase: Meals " + meals.toString() + " doc " + doc.get("mealId"));
                        }
                        Log.i(TAG, "onComplete: email"+sharedPreferences.getString("email",null));
                    }
                    netwokCallBack.onSuccessResult(meals);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                netwokCallBack.onErrorResult(e.getMessage());
            }
        });
    }

    @Override
    public void logOut() {
            auth.signOut();
    }
}
