package com.example.foodplannerapp.favourites_feature.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplannerapp.database.LocalDataSourceImpl;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.favourites_feature.presenter.FavouritesPresenter;
import com.example.foodplannerapp.favourites_feature.presenter.FavouritesPresenterImpl;
import com.example.foodplannerapp.favourites_feature.repository.FavouritesRepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.OnFavClickListener;
import com.example.foodplannerapp.meals_feature.view.OnMealClickListener;
import com.example.foodplannerapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritesFragment extends Fragment implements FavouritesView, OnFavClickListener, OnMealClickListener {


    FavouritesPresenter presenter;
    GridLayoutManager favManager;

    RecyclerView favRecyclerView;

    List<Meal> meals;
    FavouritesAdapter favAdapter;
    LottieAnimationView noFavs;
    private final static String TAG = "HomeFragment";

    public FavouritesFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meals = new ArrayList<>();
        presenter = FavouritesPresenterImpl.getInstance(

                FavouritesRepositoryImpl.getInstance(
                        LocalDataSourceImpl.getInstance(getContext())
                ), this
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRecyclerView = view.findViewById(R.id.favsRecyclerView);
        noFavs = view.findViewById(R.id.noFavs);
        favAdapter = new FavouritesAdapter(getContext(), meals, this, this);
        favManager = new GridLayoutManager(getContext(), 2);
        favManager.setOrientation(LinearLayoutManager.VERTICAL);
        favRecyclerView.setLayoutManager(favManager);
        favRecyclerView.setAdapter(favAdapter);
        Log.i(TAG, "onViewCreated: " + meals.size());
        presenter.getAllFavouritesMeals();
    }

    @SuppressLint("CheckResult")
    @Override
    public void setFavouritesList(Flowable<List<Meal>> meals) {
        List<Meal> mealList = new ArrayList<>();
        meals.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
                    Log.i(TAG, "setProductsList: subscribe" + item);
                    if (item.size() == 0)
                        noFavs.setVisibility(View.VISIBLE);
                    favAdapter.setMeals(item);
                    favAdapter.notifyDataSetChanged();
                },
                error -> {
                    Log.i(TAG, "setProductsList: Rrror" + error.getMessage());
                }
        );
    }

    @Override
    public void OnFavClickListener(Meal meal) {
        presenter.removeMealFromFavourites(meal);
    }


    @Override
    public void OnMealClickListener(String id, View view) {
        FavouritesFragmentDirections.ActionFavouritesFragmentToMealDetailsFragment2 action =
                FavouritesFragmentDirections.actionFavouritesFragmentToMealDetailsFragment2(id);
        Navigation.findNavController(view).navigate(action);
    }
}