package com.example.foodplannerapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public class FavouritesLocalDataSourceImpl implements FavouritesLocalDataSource {

    private FavouritesDAO favouritesDAO;
    private Context context;
    private PlanDAO dao;
    private Flowable<List<Plan>> plans;
    private Flowable<List<Meal>> meals;
    private DailyMealDAO dailyMealDAO;
    private DialyMeal meal;
    private static FavouritesLocalDataSourceImpl instance = null;

    private FavouritesLocalDataSourceImpl(Context context) {
        this.context = context;
        Database database = Database.getInstance(context);
        favouritesDAO = database.getFavouritesDao();
        dailyMealDAO = database.getDailyMealDao();
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
    public void insertDailyMeal(DialyMeal meal) {
        new Thread() {
            @Override
            public void run() {
                dailyMealDAO.insertDailyMeal(meal);
            }
        }.start();
    }

    @Override
    public void removeDailyMeal() {
        new Thread() {
            @Override
            public void run() {
                dailyMealDAO.removeDailyMeal();
            }
        }.start();
    }

    @Override
    public Maybe<DialyMeal> getDailyMeal(String date) {
        return dailyMealDAO.getDailyMeal(date);
    }

    @Override
    public Flowable<List<Meal>> getAllFavMeals() {
        return meals;
    }

    @Override
    public Flowable<Meal> getFavMealById(String id) {
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
    public Flowable<List<Plan>> getAllPlans() {
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
    public Flowable<Plan> getPlaneByDate(String date) {

        return dao.getPlaneByDate(date);
    }

    @Override
    public void deleteAllFavMeals() {
        new Thread(){
            @Override
            public void run() {
                favouritesDAO.deleteAllFavMeal();
            }
        }.start();
    }

    @Override
    public void deleteAllPlans() {
        new Thread(){
            @Override
            public void run() {
                dao.deleteAllPlans();
            }
        }.start();
    }

    @Override
    public void insertAllFavouries(List<Meal> meals) {
        new Thread(){
            @Override
            public void run() {
                favouritesDAO.insertAllFavourites(meals);
            }
        }.start();
    }

    @Override
    public void insertAllPlans(List<Plan> plans) {
        new Thread(){
            @Override
            public void run() {
                dao.insertAllPlans(plans);
            }
        }.start();
    }
}
