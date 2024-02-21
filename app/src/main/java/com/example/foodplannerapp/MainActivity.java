package com.example.foodplannerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.foodplannerapp.R;

import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.auth_feature.view.LoginActivity;
import com.example.foodplannerapp.auth_feature.view.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private SharedPreferences sharedPreferences;
    private static final int homeFragment = R.id.homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottomNav);


        navController = Navigation.findNavController(this, R.id.nav_host_home_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {


            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.homeFragment || navDestination.getId() == R.id.searchFragment2 ||
                        navDestination.getId() == R.id.favouritesFragment || navDestination.getId() == R.id.weeklyPlanFragment
                        || navDestination.getId() == R.id.profileFragment2) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                } else {
                    bottomNavigationView.setVisibility(View.GONE);
                }
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        Constants.navigate(id, navController, sharedPreferences, MainActivity.this);
                        return false;
                    }
                }

                );


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        new MaterialAlertDialogBuilder(this)
//                .setTitle("Do you really want to exit the App?")
//                .setPositiveButton("exit", (dialog, which) -> {
//                    finish();
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                })
//                .show();
    }
}

