package com.example.foodplannerapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Plan;

import java.util.List;

public class PlanLocalDataSourceImpl implements PlanLocalDataSource{
    private PlanDAO dao;
    private Context context;
    private LiveData<List<Plan>> plans;
    private static PlanLocalDataSourceImpl instance = null;

    private PlanLocalDataSourceImpl(Context context)
    {
        this.context=context;
        Database planDatabase = Database.getInstance(context);
        dao = planDatabase.getPlanDao();
        plans = dao.getAllPlans();

    }
    public  static PlanLocalDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new PlanLocalDataSourceImpl(context);
        }
        return instance;
    }
    @Override
    public LiveData<List<Plan>> getAllPlans() {
        return plans;
    }

    @Override
    public void addPlan(Plan plan) {
        new Thread(){
            @Override
            public void run() {
                dao.addPlan(plan);
            }
        }.start();
    }

    @Override
    public void removePlan(Plan plan) {
        new Thread(){
            @Override
            public void run() {
                dao.removePlan(plan);
            }
        }.start();
    }

    @Override
    public void updatePlan(Plan plan) {
        new Thread(){
            @Override
            public void run() {
                dao.updatePlan(plan);
            }
        }.start();
    }

    @Override
    public LiveData<Plan> getPlaneByDate(String date) {

        return dao.getPlaneByDate(date);
    }
}
