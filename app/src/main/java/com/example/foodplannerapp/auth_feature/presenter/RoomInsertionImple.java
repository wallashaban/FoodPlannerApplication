package com.example.foodplannerapp.auth_feature.presenter;

import com.example.foodplannerapp.favourites_feature.repository.FavouritesRepository;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public class RoomInsertionImple implements RoomInsertion{
    FavouritesRepository repository;
    private static RoomInsertionImple instance = null;
    private RoomInsertionImple(FavouritesRepository repository){
        this.repository = repository;
    }
    public static RoomInsertionImple getInstance(FavouritesRepository repository)
    {
        if (instance == null) {
            instance = new RoomInsertionImple(repository);
        }
        return instance;
    }

    @Override
    public void AddAllFavourites(List<Meal> meals) {
       repository.addAllFavourites(meals);
    }

    @Override
    public void AddAllPlans(List<Plan> plans) {
        repository.addAllPlans(plans);
    }
}
