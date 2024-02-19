package com.example.foodplannerapp.meals_plan_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.favourites_feature.view.FavouritesFragment;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.CategoryAdapter;
import com.example.foodplannerapp.meals_feature.view.MealAdapter;
import com.example.foodplannerapp.meals_feature.view.OnMealClickListener;
import com.example.foodplannerapp.meals_plan_feature.presenter.WeeklyPlanPresenter;
import com.example.foodplannerapp.meals_plan_feature.presenter.WeeklyPlanPresenterImpl;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Meal;
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
    WeeklyPlanPresenter presenter;

    WeeklyPlanAdapter adapter;
    List<Plan> plans;

    LinearLayoutManager manager;
    RecyclerView weeklyPlanRecyclerView;
    ConstraintLayout constraintLayout;
    LottieAnimationView noPlans;
    public WeeklyPlanFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plans = new ArrayList<>();
       presenter = WeeklyPlanPresenterImpl.getInstance(
               this, RepositoryImpl.getInstance(
                       RemoteDataSourceImpl.getInstance(),
                       FavouritesLocalDataSourceImpl.getInstance(getContext())),
                       FirebaseCrudRepositoryImpl
                               .getInstance(FirebaseRemoteDataSourceImpl.getInstance(getContext())
                               )

       );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weeklyPlanRecyclerView = view.findViewById(R.id.weekly_plan_recycler_view);
        adapter = new WeeklyPlanAdapter(getContext(), plans, this,this);
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

    @Override
    public void setPlansList(Flowable<List<Plan>> plans) {
        List<Plan> planList = new ArrayList<>();
        plans.subscribeOn(Schedulers.io()).doOnNext(
                item-> Log.i(TAG, "setProductsList: Next"+item)
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(item->{
                    Log.i(TAG, "setProductsList: subscribe"+item);
                    // pros = item;
            if(item.size()==0)
                noPlans.setVisibility(View.VISIBLE);

                    adapter.setMealsPlan(item);
                    adapter.notifyDataSetChanged();
                },
                error->{
                    Log.i(TAG, "setProductsList: Rrror"+error.getMessage());
                },
                ()->{
                    Log.i(TAG, "setProductsList: OnComplete");
                    //adapter.setProducts(pros);
                    //adapter.notifyDataSetChanged();
                }
        );
//        plans.observe(this, new Observer<List<Plan>>() {
//            @Override
//            public void onChanged(List<Plan> plans) {
//                WeeklyPlanFragment.this.plans = plans;
//                adapter.setMealsPlan(plans);
//                Log.i(TAG, "setPlansList: " + plans.size());
//                adapter.notifyDataSetChanged();
//            }
//        });
    }
}