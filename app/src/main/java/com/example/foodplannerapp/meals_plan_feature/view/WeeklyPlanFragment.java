package com.example.foodplannerapp.meals_plan_feature.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.database.LocalDataSourceImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.OnMealClickListener;
import com.example.foodplannerapp.meals_plan_feature.presenter.WeeklyPlanPresenter;
import com.example.foodplannerapp.meals_plan_feature.presenter.WeeklyPlanPresenterImpl;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class WeeklyPlanFragment extends Fragment implements WeeklyPlanView,
        OnPlanClickListener, OnMealClickListener {

    private static final String TAG = "WeeklyPlanFragment";
    private WeeklyPlanPresenter presenter;

    private WeeklyPlanAdapter adapter;
    private List<Plan> plans;

    private LinearLayoutManager manager;
    private RecyclerView weeklyPlanRecyclerView;
    private ConstraintLayout constraintLayout;
    private LottieAnimationView noPlans;

    public WeeklyPlanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plans = new ArrayList<>();
        presenter = WeeklyPlanPresenterImpl.getInstance(
                this, RepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        LocalDataSourceImpl.getInstance(getContext())),
                FirebaseCrudRepositoryImpl
                        .getInstance(FirebaseRemoteDataSourceImpl.getInstance(getContext())
                        )

        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weeklyPlanRecyclerView = view.findViewById(R.id.weekly_plan_recycler_view);
        adapter = new WeeklyPlanAdapter(getContext(), plans, this, this);
        manager = new LinearLayoutManager(getContext());

        noPlans = view.findViewById(R.id.noPlans);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        weeklyPlanRecyclerView.setLayoutManager(manager);
        weeklyPlanRecyclerView.setAdapter(adapter);

        presenter.getAllPlansMeals();
    }

    @Override
    public void OnMealClickListener(String id, View view) {
        WeeklyPlanFragmentDirections.ActionWeeklyPlanFragmentToMealDetailsFragment2 action =
                WeeklyPlanFragmentDirections.actionWeeklyPlanFragmentToMealDetailsFragment2(id);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onPlanButtonClickListener(Plan plan) {

        presenter.removePlan(plan);
        presenter.removeMealFromPlanUsingFirebase(plan);
    }

    @SuppressLint("CheckResult")
    @Override
    public void setPlansList(Flowable<List<Plan>> plans) {
        List<Plan> planList = new ArrayList<>();
        plans.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
                    if (item.size() == 0)
                        noPlans.setVisibility(View.VISIBLE);
                    adapter.setMealsPlan(item);
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Log.i(TAG, "setProductsList: Rrror" + error.getMessage());
                }
        );
    }
}