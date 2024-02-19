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
import com.example.foodplannerapp.auth_feature.presenter.RegisterPresenter;
import com.example.foodplannerapp.auth_feature.presenter.RegisterPresenterImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepositoryImpl;
import com.example.foodplannerapp.models.AuthParameters;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    TextInputEditText email,password,username;
    Button register;
    RegisterPresenter presenter;
    TextView signin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter =  RegisterPresenterImpl.getInstance(
                FirebaseAuthRepositoryImpl.getInstance(
                        FirebaseRemoteDataSourceImpl.getInstance(this)
                ),this
        );
        progressBar = findViewById(R.id.registerProgressBar);
        signin = findViewById(R.id.signin);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                 String user = username.getText().toString().trim();

                //Validation check
                if (TextUtils.isEmpty(user)) {
                    username.setError("Enter Your name");
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(emailInput)) {
                    email.setError("Enter Your email");
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    password.setError("Enter Your password");
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    password.setError("Password too short, enter minimum 6 characters!");
                    //Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                {
                    presenter.registerUserByEmailAndPassword(
                            new AuthParameters(emailInput, passwordInput, user, RegisterActivity.this));
                    progressBar.setVisibility(View.VISIBLE);
                    register.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(RegisterActivity.this, "Authentication failed." + errorMessage,
                Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        register.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}