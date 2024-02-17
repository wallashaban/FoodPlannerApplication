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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
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

    Retrofit retrofit;
    private static RemoteDataSourceImpl instance = null;

    private RemoteDataSourceImpl() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build(); //Q
        services = retrofit.create(ApiServices.class);//Q
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
        Single<MealsObject> randomMealObservable = services.getRandomMeal();
        randomMealObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onRandomMealSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onRandomMealFailureResult(error.getMessage());
                        }
                );

    }

    @Override
    public void getMealByIdNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String id) {
        Single<MealsObject> mealObservable = services.getMealByID(id);
        mealObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onFailureResult(error.getMessage());
                        }
                );
    }

    @Override
    public void searchMealByNameNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String name) {
        Single<MealsObject> searchMealObservable = services.searchMealByName(name);
        searchMealObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onFailureResult(error.getMessage());
                        }
                );
    }

    @Override
    public void filterMealByMainIngredientNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String ingredient) {
        Single<MealsObject> filterMealByMainIngredientObservable = services.filterMealByMainIngredient(ingredient);
        filterMealByMainIngredientObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onFailureResult(error.getMessage());
                        }
                );
          }

    @Override
    public void filterMealByCategoryNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey) {
        Single<MealsObject> filterMealByCategoryObservable = services.filterMealByCategory(categoey);
        filterMealByCategoryObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onFailureResult(error.getMessage());
                        }
                );
        }

    @Override
    public void searchMealByFirstLetterNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String firstLetter) {
        Single<MealsObject> searchMealByFirstLetter = services.searchMealByFirstLetter(firstLetter);
        searchMealByFirstLetter.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onFailureResult(error.getMessage());
                        }
                );
    }

    @Override
    public void getAllCategoriesNetworkCallBack(CategoriesNetworkCallBAck networkCallBAck) {
        Single<CategoryObject> allCategoriesObservable = services.getAllCategories();
        allCategoriesObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onCategoriesSuccessResult(response.getCategories());
                        },
                        error->{
                            networkCallBAck.onCategoriesFailureResult(error.getMessage());
                        }
                );
    }

    @Override
    public void getAllAreasNetworkCallBack(AreaNetworkCallBAck networkCallBAck) {
        Single<AreaObject> allAreasObservable = services.getAllAreas();
        allAreasObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onAreasSuccessResult(response.getAreas());
                        },
                        error->{
                            networkCallBAck.onAreasFailureResult(error.getMessage());
                        }
                );
    }

    @Override
    public void getAllIngredientsNetworkCallBack(IngredientsNetworkCallBAck networkCallBAck) {
        Single<IngredientsObject> allIngredientsObservable = services.getAllIngredients();
        allIngredientsObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onIngredientsSuccessResult(response.getIngredients());
                        },
                        error->{
                            networkCallBAck.onIngredientsFailureResult(error.getMessage());
                        }
                );
    }

    @Override
    public void filterMealByAreaNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String area) {
        Single<MealsObject> mealByAreaObservable = services.filterMealByArea(area);
        mealByAreaObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            networkCallBAck.onSuccessResult(response.getMeals());
                        },
                        error->{
                            networkCallBAck.onFailureResult(error.getMessage());
                        }
                );
    }
}
