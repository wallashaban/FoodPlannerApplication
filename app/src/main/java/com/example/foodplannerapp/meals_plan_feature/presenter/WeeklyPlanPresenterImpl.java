package com.example.foodplannerapp.meals_plan_feature.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.meals_feature.presenter.HomePresenterImpl;
import com.example.foodplannerapp.meals_feature.view.HomeView;
import com.example.foodplannerapp.meals_plan_feature.view.WeeklyPlanView;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public class WeeklyPlanPresenterImpl implements WeeklyPlanPresenter{
    private WeeklyPlanView view;
    private Repository repository;

    private static WeeklyPlanPresenterImpl instance = null;

    private WeeklyPlanPresenterImpl(WeeklyPlanView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }


    public static WeeklyPlanPresenterImpl getInstance(WeeklyPlanView view, Repository repository) {
        if (instance == null) {
            instance = new WeeklyPlanPresenterImpl(view, repository);
        }else {
            instance.view = view;
        }
        return instance;
    }

    @Override
    public void getAllPlansMeals() {
       LiveData<List<Plan>> plans = repository.getAllPlans();
        view.setPlansList(plans);
    }



    @Override
    public void removePlan(Plan plan) {
        repository.removePlan(plan);
    }
}
