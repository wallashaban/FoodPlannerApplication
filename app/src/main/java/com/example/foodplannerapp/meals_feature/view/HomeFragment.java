package com.example.foodplannerapp.meals_feature.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.auth_feature.view.LoginActivity;
import com.example.foodplannerapp.database.LocalDataSourceImpl;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepositoryImpl;
import com.example.foodplannerapp.meals_feature.presenter.HomePresenter;
import com.example.foodplannerapp.meals_feature.presenter.HomePresenterImpl;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnFavClickListener
        , OnCategoryClickListener, OnRandomMealClickListener, OnMealPlanClickListener,
        DatePickerDialogListener {

    private HomePresenter presenter;

    private CategoryAdapter categoryAdapter;
    private List<Category> categories;
    private List<Meal> meals;
    private MealAdapter mealsAdapter;

    private LinearLayoutManager categoryManager;
    private RecyclerView categoeryRecyclerView;
    private RecyclerView mealsRecyclerView;
    private RecyclerView categoryRecyclerView;
    private GridLayoutManager mealsManager;
    private Button planButton;

    private ConstraintLayout constraintLayout;
    private TextView mealName;
    private Meal meal;
    private Button favButton;
    private ImageView mealImage;
    private ScrollView scrollView;
    private final static String TAG = "HomeFragment";
    private LottieAnimationView noInternetAnimation;
    private Group homeGroup;
    private ProgressBar homeProgressBar;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        meals = new ArrayList<>();
        presenter = HomePresenterImpl.getInstance(

                this, RepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        LocalDataSourceImpl.getInstance(getContext())),
                FirebaseCrudRepositoryImpl
                        .getInstance(FirebaseRemoteDataSourceImpl.getInstance(getContext())
                        )
        );// builder pattern

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeProgressBar = view.findViewById(R.id.searchProgressBar);
        homeGroup = view.findViewById(R.id.home_group);
        noInternetAnimation = view.findViewById(R.id.no_internet_animation_search);
        // Meal of the day
        mealName = view.findViewById(R.id.searchName);
        mealImage = view.findViewById(R.id.searchImage);
        favButton = view.findViewById(R.id.favButton);
        constraintLayout = view.findViewById(R.id.planConstrainLayout);
        //Categories
        categoeryRecyclerView = view.findViewById(R.id.categories);
        mealsRecyclerView = view.findViewById(R.id.suggestedMeals);
        mealsAdapter = new MealAdapter(getContext(), meals, this, this);
        categoryManager = new LinearLayoutManager(getContext());
        mealsManager = new GridLayoutManager(getContext(), 2);

        categoryAdapter = new CategoryAdapter(getContext(), categories, this);
        categoryManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mealsManager.setOrientation(LinearLayoutManager.VERTICAL);

        categoeryRecyclerView.setLayoutManager(categoryManager);
        categoeryRecyclerView.setAdapter(categoryAdapter);
        mealsRecyclerView.setLayoutManager(mealsManager);
        mealsRecyclerView.setAdapter(mealsAdapter);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isLogedIn(getContext()))
                    Constants.showDialog(getActivity(), LoginActivity.class);
                else {
                    presenter.addMealToFavourites(meal);
                    presenter.addMealToFavouriteUsingFirebase(meal);
                    Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });
        planButton = view.findViewById(R.id.planMealRandomButton);
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isLogedIn(getContext()))
                    Constants.showDialog(getActivity(), LoginActivity.class);
                else
                    Constants.showDatePicker(getContext(), HomeFragment.this);
            }
        });
        constraintLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment2 action =
                                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment2(meal.getMealId());
                        Navigation.findNavController(view).navigate(action);
                    }
                }
        );

        if (categories.size() == 0) {
            presenter.getAllCategories(categories);
        } else {
            homeProgressBar.setVisibility(View.GONE);
            homeGroup.setVisibility(View.VISIBLE);
        }
        if (meals.isEmpty()) {
            Log.i(TAG, "onStart: Meals");
            presenter.getAllMeals(meals);
        } else {
            homeProgressBar.setVisibility(View.GONE);
            homeGroup.setVisibility(View.VISIBLE);
        }
        setDailyMeal(getDailyMeal(Constants.getDate()));
    }


    @Override
    public Maybe<DialyMeal> getDailyMeal(String date) {
        return presenter.getDailyMeal(date);
    }

    private void setDailyMealData(Meal meal)
    {
        Glide.with(getContext()).load(this.meal.getMealThumb())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                        .error(R.drawable.ic_launcher_background))
                .into(mealImage);
        mealName.setText(this.meal.getMealName());
    }
    @SuppressLint("CheckResult")
    @Override
    public void setDailyMeal(Maybe<DialyMeal> meal) {
        meal.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
                            this.meal = item.getMeal();
                            if (this.meal != null) {
                               setDailyMealData(this.meal);
                            }
                        },
                        error -> {
                            Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

                            Log.i(TAG, "setProductsList: Rrror" + error.getMessage());
                        },
                        () -> {
                            if (this.meal == null) {
                                removeDalyMeal();
                                presenter.getRandomMeal();
                            }
                        }
                );
    }

    @Override
    public void insertDalyMeal(DialyMeal meal) {
        presenter.insertDalyMeal(meal);
    }

    @Override
    public void removeDalyMeal() {
        presenter.removeDalyMeal();
    }


    @Override
    public void showMealsData(List<Meal> meals) {
        Log.i(TAG, "showMealsData: " + meals);
        homeProgressBar.setVisibility(View.GONE);
        homeGroup.setVisibility(View.VISIBLE);
        this.meals = meals;
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMealsErrorMessage(String errorMessage) {

        if (errorMessage.equals("No internet connection")) {
            homeGroup.setVisibility(View.INVISIBLE);
            homeProgressBar.setVisibility(View.INVISIBLE);
            Log.i(TAG, "showRandomMealErrorMessage: value" + homeGroup.getVisibility());
            noInternetAnimation.setVisibility(View.VISIBLE);
        }
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCategoriesData(List<Category> categories) {
        this.categories = categories;
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesErrorMessage(String errorMessage) {
        Log.i(TAG, "showCategoriesErrorMessage: " + errorMessage);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMealData(Meal meal) {
        this.meal = meal;
        DialyMeal dialyMeal = new DialyMeal(Constants.getDate(), meal);
        insertDalyMeal(dialyMeal);
        setDailyMealData(meal);
    }

    @Override
    public void onNetworkFailure(String errorMessage) {
    }

    @Override
    public void showRandomMealErrorMessage(String errorMessage) {
        Log.i(TAG, "showRandomMealErrorMessage: after" + errorMessage);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void OnFavClickListener(Meal meal) {
        if (Constants.isLogedIn(getContext()))
            Constants.showDialog(getActivity(), LoginActivity.class);
        else {
            presenter.addMealToFavourites(meal);
            presenter.addMealToFavouriteUsingFirebase(meal);
            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryClickListener(String category, View view) {
        HomeFragmentDirections.ActionHomeFragmentToAllMealsFragment3 action =
                HomeFragmentDirections.actionHomeFragmentToAllMealsFragment3();
        action.setCategory(category);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void OnMealClickListener(String id, View view) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment2 action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment2(id);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void OnRandomMealClickListener(String id, View view) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment2 action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment2(id);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onMealPlanClickListener(Plan plan) {
        if (Constants.isLogedIn(getContext()))
            Constants.showDialog(getActivity(), LoginActivity.class);
        else {
            presenter.addMealToPlan(plan);
            presenter.addMealToPlanUsingFirebase(plan);
            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(Calendar selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(selectedDate.getTime());
        Plan plan = new Plan(formattedDate,
                meal);
        onMealPlanClickListener(plan);
        presenter.addMealToPlanUsingFirebase(plan);
        Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
    }
}