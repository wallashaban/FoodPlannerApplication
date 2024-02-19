package com.example.foodplannerapp.auth_feature.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.auth_feature.presenter.LoginPresenter;
import com.example.foodplannerapp.auth_feature.presenter.LoginPresenterImpl;
import com.example.foodplannerapp.auth_feature.presenter.RoomInsertion;
import com.example.foodplannerapp.auth_feature.presenter.RoomInsertionImple;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.favourites_feature.repository.FavouritesRepositoryImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepositoryImpl;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements
LoginView{

    LoginPresenter presenter;
    TextInputEditText email,password;
    Button login;
    RoomInsertion insertion;
    Button skip;
    TextView signup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        presenter = LoginPresenterImpl.getInstance(
                FirebaseAuthRepositoryImpl.getInstance(
                        FirebaseRemoteDataSourceImpl.getInstance(this)
                ),this
        );
        insertion = RoomInsertionImple.getInstance(FavouritesRepositoryImpl.getInstance(
                FavouritesLocalDataSourceImpl.getInstance(this)
        ));
        progressBar = findViewById(R.id.loginProgressBar);
        skip = findViewById(R.id.skip);
        signup = findViewById(R.id.signup);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.loginBtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();


                //Validation section
                if (TextUtils.isEmpty(emailInput)) {
                    email.setError("Enter email address");
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Enter password");
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                if (password.length() < 6) {
                    password.setError("Should be greater than 6");
                }
                {
                    presenter.loginUserWithEmailAndPassword(new AuthParameters(emailInput, pass, "", LoginActivity.this));
                    progressBar.setVisibility(View.VISIBLE);
                    login.setVisibility(View.INVISIBLE);
                }
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
        progressBar.setVisibility(View.INVISIBLE);
        login.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPlansErrorMessage(String errorMessage) {

    }

    @Override
    public void showPlansData(List<Plan> plans) {
        insertion.AddAllPlans(plans);
        showData();
    }

    @Override
    public void showMealsErrorMessage(String errorMessage) {

    }

    @Override
    public void showMealsData(List<Meal> meals) {
        insertion.AddAllFavourites(meals);
    }
}