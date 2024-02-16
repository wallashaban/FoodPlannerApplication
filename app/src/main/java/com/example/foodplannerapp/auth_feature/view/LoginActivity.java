package com.example.foodplannerapp.auth_feature.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.auth_feature.presenter.LoginPresenter;
import com.example.foodplannerapp.auth_feature.presenter.LoginPresenterImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepositoryImpl;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements
LoginView{

    LoginPresenter presenter;
    TextInputEditText email,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        presenter = LoginPresenterImpl.getInstance(
                FirebaseAuthRepositoryImpl.getInstance(
                        FirebaseRemoteDataSourceImpl.getInstance(this)
                ),this
        );
        Meal meal = new Meal();
        meal.setMealId("4567");
        meal.setMealName("Pizza");
        meal.setMealThumb("bla bla");
        Plan plan = new Plan();
        plan.setDate("14/12/2012");
        plan.setMeal(meal);
        FirebaseRemoteDataSourceImpl.getInstance(this).removeMealFromFavourite(meal);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.loginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();

                //Validation section
                if (TextUtils.isEmpty(emailInput)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                if (password.length() < 6) {
                    password.setError("Should be greater than 6");
                }
               presenter.loginUserWithEmailAndPassword(new AuthParameters(emailInput,pass,"",LoginActivity.this));
            }
        });
    }
    public void onLogout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(LoginActivity.this, "Authentication failed." + errorMessage,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}