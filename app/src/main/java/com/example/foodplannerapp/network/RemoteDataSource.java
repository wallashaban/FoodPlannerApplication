package com.example.foodplannerapp.network;

public interface RemoteDataSource {
    public void downLoadVideo(MealsNetworkCallBAck networkCallBAck,String url);
    public void getRandomMealsNetworkCallBack(RandomMealNetworkCallBAck networkCallBAck);

    public void getMealByIdNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String id);

    public void searchMealByNameNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String name);

    public void filterMealByMainIngredientNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String ingredient);

    public void filterMealByCategoryNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey);

    public void searchMealByFirstLetterNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String firstLetter);

    public void getAllCategoriesNetworkCallBack(CategoriesNetworkCallBAck networkCallBAck);

    public void getAllAreasNetworkCallBack(AreaNetworkCallBAck networkCallBAck);

    public void getAllIngredientsNetworkCallBack(IngredientsNetworkCallBAck networkCallBAck);

    public void filterMealByAreaNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey);

}
