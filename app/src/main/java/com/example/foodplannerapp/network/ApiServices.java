package com.example.foodplannerapp.network;


import com.example.foodplannerapp.models.AreaObject;
import com.example.foodplannerapp.models.CategoryObject;
import com.example.foodplannerapp.models.IngredientsObject;
import com.example.foodplannerapp.models.MealsObject;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiServices {

    @GET
    @Streaming
    Call<ResponseBody> downloadVideo(@Url String url);

    @GET("search.php")
    Single<MealsObject> searchMealByName(@Query("s") String name);

    @GET("search.php")
    Single<MealsObject> searchMealByFirstLetter(@Query("f") String firstLetter);

    @GET("lookup.php")
    Single<MealsObject> getMealByID(@Query("i") String id);

    @GET("random.php")
    Single<MealsObject> getRandomMeal();

    @GET("categories.php")
    Single<CategoryObject> getAllCategories();

    @GET("list.php?a=list")
    Single<AreaObject> getAllAreas();

    @GET("list.php?i=list")
    Single<IngredientsObject> getAllIngredients();

    @GET("filter.php")
    Single<MealsObject> filterMealByMainIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealsObject> filterMealByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealsObject> filterMealByArea(@Query("a") String area);

}
