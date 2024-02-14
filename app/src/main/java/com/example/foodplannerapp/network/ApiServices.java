package com.example.foodplannerapp.network;


import com.example.foodplannerapp.models.AreaObject;
import com.example.foodplannerapp.models.CategoryObject;
import com.example.foodplannerapp.models.IngredientsObject;
import com.example.foodplannerapp.models.MealsObject;

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
    Call<MealsObject> searchMealByName(@Query("s") String name);

    @GET("search.php")
    Call<MealsObject> searchMealByFirstLetter(@Query("f") String firstLetter);

    @GET("lookup.php")
    Call<MealsObject> getMealByID(@Query("i") String id);

    @GET("random.php")
    Call<MealsObject> getRandomMeal();

    @GET("categories.php")
    Call<CategoryObject> getAllCategories();

    @GET("list.php?a=list")
    Call<AreaObject> getAllAreas();

    @GET("list.php?i=list")
    Call<IngredientsObject> getAllIngredients();

    @GET("filter.php")
    Call<MealsObject> filterMealByMainIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealsObject> filterMealByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealsObject> filterMealByArea(@Query("a") String area);

}
