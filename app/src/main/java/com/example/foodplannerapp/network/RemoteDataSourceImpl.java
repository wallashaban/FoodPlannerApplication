package com.example.foodplannerapp.network;

import android.util.Log;

import com.example.foodplannerapp.models.Area;
import com.example.foodplannerapp.models.AreaObject;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.CategoryObject;
import com.example.foodplannerapp.models.Ingredient;
import com.example.foodplannerapp.models.IngredientsObject;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.MealsObject;

import java.util.ArrayList;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource {
    private final static String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final ApiServices services;
    private final static String TAG = "RemoteDataSource";
    List<Meal> meals;

    List<Area> areas;
    List<Category> categories;
    List<Ingredient> ingredients;
    Retrofit retrofit;
    private static RemoteDataSourceImpl instance = null;

    private RemoteDataSourceImpl() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build(); //Q
        services = retrofit.create(ApiServices.class);//Q
        meals = new ArrayList<>();
        categories = new ArrayList<>();
        areas = new ArrayList<>();
        ingredients = new ArrayList<>();

    }

    public static RemoteDataSourceImpl getInstance() {
        if (instance == null) {
            instance = new RemoteDataSourceImpl();
        }
        return instance;
    }

    @Override
    public void downLoadVideo(MealsNetworkCallBAck networkCallBAck, String url) {
        Call<ResponseBody> call= services.downloadVideo(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getRandomMealsNetworkCallBack(RandomMealNetworkCallBAck networkCallBAck) {
        Call<MealsObject> call = services.getRandomMeal();
       // randomMealObservable.subscribeOn(Scheduler)
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                if (response.isSuccessful()) {
                    meals = response.body().getMeals();
                    networkCallBAck.onRandomMealSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onRandomMealFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealByIdNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String id) {
        Call<MealsObject> call = services.getMealByID(id);
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                if (response.isSuccessful()) {
                    //meals = response.body().getMeals();
                    networkCallBAck.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void searchMealByNameNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String name) {
        Call<MealsObject> call = services.searchMealByName(name);
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                if (response.isSuccessful()) {
                    //meals = response.body().getMeals();
                    networkCallBAck.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void filterMealByMainIngredientNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String ingredient) {
        Call<MealsObject> call = services.filterMealByMainIngredient(ingredient);
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                if (response.isSuccessful()) {
                    //meals = response.body().getMeals();
                    networkCallBAck.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void filterMealByCategoryNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey) {
        Call<MealsObject> call = services.filterMealByCategory(categoey);
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                if (response.isSuccessful()) {
                    // meals = response.body().getMeals();
                    networkCallBAck.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void searchMealByFirstLetterNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String firstLetter) {
        Call<MealsObject> call = services.searchMealByFirstLetter(firstLetter);
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                if (response.isSuccessful()) {
                    MealsObject object = response.body();
                    Log.i(TAG, "onResponse: Meals " + meals.size());

                    networkCallBAck.onSuccessResult(object.getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAllCategoriesNetworkCallBack(CategoriesNetworkCallBAck networkCallBAck) {
        Call<CategoryObject> call = services.getAllCategories();
        call.enqueue(new Callback<CategoryObject>() {
            @Override
            public void onResponse(Call<CategoryObject> call, Response<CategoryObject> response) {
                if (response.isSuccessful()) {
                    CategoryObject object = response.body();
                    Log.i(TAG, "onResponse: Categories " + categories.size());
                    networkCallBAck.onCategoriesSuccessResult(object.getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onCategoriesFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAllAreasNetworkCallBack(AreaNetworkCallBAck networkCallBAck) {
        Call<AreaObject> call = services.getAllAreas();
        call.enqueue(new Callback<AreaObject>() {
            @Override
            public void onResponse(Call<AreaObject> call, Response<AreaObject> response) {
                if (response.isSuccessful()) {
                    areas = response.body().getAreas();
                    networkCallBAck.onAreasSuccessResult(areas);
                }
            }

            @Override
            public void onFailure(Call<AreaObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onAreasFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAllIngredientsNetworkCallBack(IngredientsNetworkCallBAck networkCallBAck) {
        Call<IngredientsObject> call = services.getAllIngredients();
        call.enqueue(new Callback<IngredientsObject>() {
            @Override
            public void onResponse(Call<IngredientsObject> call, Response<IngredientsObject> response) {
                if (response.isSuccessful()) {
                    ingredients = response.body().getIngredients();
                    networkCallBAck.onIngredientsSuccessResult(ingredients);
                }
            }

            @Override
            public void onFailure(Call<IngredientsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onIngredientsFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void filterMealByAreaNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String area) {
        Call<MealsObject> call = services.filterMealByArea(area);
        call.enqueue(new Callback<MealsObject>() {
            @Override
            public void onResponse(Call<MealsObject> call, Response<MealsObject> response) {
                networkCallBAck.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsObject> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallBAck.onFailureResult(t.getMessage());
            }
        });
    }
}
