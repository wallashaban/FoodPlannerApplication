package com.example.foodplannerapp.auth_feature.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.auth_feature.presenter.ProfilePresenter;
import com.example.foodplannerapp.auth_feature.presenter.ProfilePresenterImpl;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseAuthRepositoryImpl;
import com.google.android.material.textfield.TextInputEditText;


public class profileFragment extends Fragment {

    TextInputEditText email,username;
    Button logout;
    SharedPreferences sharedPreferences;
    ProfilePresenter presenter;
    public profileFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences
                ("auth", getContext().MODE_PRIVATE);
        presenter = ProfilePresenterImpl.getInstance(FirebaseAuthRepositoryImpl.getInstance(
                FirebaseRemoteDataSourceImpl.getInstance(getContext())
        ), FavouritesLocalDataSourceImpl.getInstance(getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username =view. findViewById(R.id.profileName);
        email =  view.findViewById(R.id.profileEmail);
        logout = view.findViewById(R.id.logout);
        String emailVal = sharedPreferences.getString("email",null);
        String name = sharedPreferences.getString("name",null);
        username.setText(name);
        email.setText(emailVal);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logOut(getContext());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}