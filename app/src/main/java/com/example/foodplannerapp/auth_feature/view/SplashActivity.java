package com.example.foodplannerapp.auth_feature.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;


public class SplashActivity extends AppCompatActivity {

    ImageButton googleBtn;
    ImageButton facebookBtn;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN = 20;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth = FirebaseAuth.getInstance();
                                Log.i("TAG", "onComplete: Name "+auth.getCurrentUser().getDisplayName());
                                Log.i("TAG", "onComplete: Email "+auth.getCurrentUser().getEmail());

                                Toast.makeText(SplashActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SplashActivity.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_default_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(SplashActivity.this, options);

        auth = FirebaseAuth.getInstance();
         googleBtn = findViewById(R.id.google);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });


//        googleBtn = findViewById(R.id.google);
//        facebookBtn = findViewById(R.id.facebook);
//        auth = FirebaseAuth.getInstance();
//        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                //.requestIdToken("372223419769-g1vpbigjdtvu8h8j2pms0bq31u5bt1u3.apps.googleusercontent.com")
//                .requestEmail()
//                .build();
//        googleSignInClient =  GoogleSignIn.getClient(this,googleSignInOptions);
//
//        googleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });
    }
    void signIn()
    {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
//        if(requestCode == RC_SIGN)
//        {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuth(account.getIdToken());
//            } catch (ApiException e) {
//                Toast.makeText(this,e.getMessage()+"error ",Toast.LENGTH_SHORT).show();
//            }
//
//        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.w("TAG", "Google sign in failed", e);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = auth.getCurrentUser();
                            // Update UI accordingly
                        } else {
                            // Sign in failed
                            // Handle failure
                        }
                    }
                });
    }


    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = auth.getCurrentUser();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id",user.getUid());
                    map.put("name",user.getDisplayName());
                    map.put("profile",user.getPhotoUrl().toString());
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(SplashActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}