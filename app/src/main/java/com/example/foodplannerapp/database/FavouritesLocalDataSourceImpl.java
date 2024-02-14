package com.example.foodplannerapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public class FavouritesLocalDataSourceImpl implements FavouritesLocalDataSource {

    private FavouritesDAO favouritesDAO;
    private Context context;
    private PlanDAO dao;
    private LiveData<List<Plan>> plans;
    private LiveData<List<Meal>> meals;
    private static FavouritesLocalDataSourceImpl instance = null;

    private FavouritesLocalDataSourceImpl(Context context) {
        this.context = context;
        Database database = Database.getInstance(context);
        favouritesDAO = database.getFavouritesDao();
        meals = favouritesDAO.getAllFavMeals(); // what if I need the products after change?
        dao = database.getPlanDao();
        plans = dao.getAllPlans();
    }

    public static FavouritesLocalDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new FavouritesLocalDataSourceImpl(context);
        }
        return instance;
    }

    @Override
    public LiveData<List<Meal>> getAllFavMeals() {
        return meals; // ?اشمعنى الداتا ديه مش ف thread
    }

    @Override
    public LiveData<Meal> getFavMealById(String id) {
        return favouritesDAO.getFavMealById(id);
    }

    @Override
    public void addMealToFavourites(Meal meal) {
        new Thread() {
            @Override
            public void run() {
                favouritesDAO.addMealToFavourites(meal);
            }
        }.start();
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        new Thread() {
            @Override
            public void run() {
                favouritesDAO.removeMealFromFavourites(meal);
            }
        }.start();
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
