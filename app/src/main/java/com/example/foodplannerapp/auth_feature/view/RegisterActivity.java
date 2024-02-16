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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter =  RegisterPresenterImpl.getInstance(
                FirebaseAuthRepositoryImpl.getInstance(
                        FirebaseRemoteDataSourceImpl.getInstance(this)
                ),this
        );
        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                 String user = username.getText().toString().trim();

                //Validation check
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(emailInput)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.registerUserByEmailAndPassword(new AuthParameters(emailInput,passwordInput,user,RegisterActivity.this));
                //progressBar.setVisibility(View.VISIBLE);

//                auth.createUserWithEmailAndPassword(emailInput, passwordInput)
//                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//
//                                if (!task.isSuccessful()) {
//                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
//                                            Toast.LENGTH_LONG).show();
//                                    Log.e("MyTag", task.getException().toString());
//                                } else {
//
//                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                                    finish();
//                                }
//                            }
//                        });
            }
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(RegisterActivity.this, "Authentication failed." + errorMessage,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}