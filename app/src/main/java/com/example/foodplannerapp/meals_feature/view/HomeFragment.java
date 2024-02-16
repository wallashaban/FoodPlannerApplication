package com.example.foodplannerapp.meals_feature.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.auth_feature.view.LoginActivity;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepository;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepositoryImpl;
import com.example.foodplannerapp.meals_feature.presenter.HomePresenter;
import com.example.foodplannerapp.meals_feature.presenter.HomePresenterImpl;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnFavClickListener
        ,OnCategoryClickListener,OnRandomMealClickListener,OnMealPlanClickListener,
DatePickerDialogListener{

    HomePresenter presenter;

    CategoryAdapter categoryAdapter;
    List<Category> categories;
    List<Meal> meals;
    MealAdapter mealsAdapter;

    LinearLayoutManager categoryManager;
    RecyclerView categoeryRecyclerView;
    RecyclerView mealsRecyclerView;
    RecyclerView categoryRecyclerView;
    GridLayoutManager mealsManager;
    CheckBox planButton;

    private ConstraintLayout constraintLayout;
    private TextView mealName;
    private Meal meal;
    private CheckBox favButton;
    private ImageView mealImage;
    private final static String TAG = "HomeFragment";

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: Home");
        categories = new ArrayList<>();
        meals = new ArrayList<>();
        presenter = HomePresenterImpl.getInstance(

                this, RepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        FavouritesLocalDataSourceImpl.getInstance(getContext())),
                FirebaseCrudRepositoryImpl
                        .getInstance(FirebaseRemoteDataSourceImpl.getInstance(getContext())
                )
        );// builder pattern

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: Home");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: Home Fragment");

        // Meal of the day
        mealName = view.findViewById(R.id.mealPlanName);
        mealImage = view.findViewById(R.id.mealPlanImage);
        favButton = view.findViewById(R.id.favButton);
        constraintLayout = view.findViewById(R.id.planConstrainLayout);
        //Categories
        categoeryRecyclerView = view.findViewById(R.id.categories);
        mealsRecyclerView = view.findViewById(R.id.suggestedMeals);
        mealsAdapter = new MealAdapter(getContext(), meals, this, this);
        categoryManager = new LinearLayoutManager(getContext());
        mealsManager = new GridLayoutManager(getContext(),2);

        categoryAdapter = new CategoryAdapter(getContext(), categories,this);
        categoryManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mealsManager.setOrientation(LinearLayoutManager.VERTICAL);

         categoeryRecyclerView.setLayoutManager(categoryManager);
         categoeryRecyclerView.setAdapter(categoryAdapter);
        mealsRecyclerView.setLayoutManager(mealsManager);
        mealsRecyclerView.setAdapter(mealsAdapter);
        Log.i(TAG, "onViewCreated: meals  :: "+meals.size());
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.isLogedIn(getContext()))
                    Constants.showDialog(getActivity(), LoginActivity.class);
                else {
                    presenter.addMealToFavourites(meal);
                    presenter.addMealToFavouriteUsingFirebase(meal);
                }

            }
        });
        planButton = view.findViewById(R.id.planMealRandomButton);
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.isLogedIn(getContext()))
                    Constants.showDialog(getActivity(), LoginActivity.class);
                else
                    Constants.showDatePicker(getContext(),HomeFragment.this);
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
        if(meal==null)
            presenter.getRandomMeal();
        else{
            Glide.with(getContext()).load(meal.getMealThumb())
                    .apply(new RequestOptions().override(200, 200)
                            .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                            .error(R.drawable.ic_launcher_background))
                    .into(mealImage);


            mealName.setText(meal.getMealName());

        }
        if (categories.isEmpty())
            presenter.getAllCategories(categories);
        if (meals.isEmpty())
            presenter.getAllMeals(meals);
    }




    @Override
    public void showMealsData(List<Meal> meals) {
        this.meals = meals;
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMealsErrorMessage(String errorMessage) {
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
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMealData(Meal meal) {
        this.meal = meal;
        Glide.with(getContext()).load(meal.getMealThumb())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                        .error(R.drawable.ic_launcher_background))
                .into(mealImage);


        //progress.setVisibility(View.INVISIBLE);
        //mealGroup.setVisibility(View.VISIBLE);
        mealName.setText(meal.getMealName());
    }

    @Override
    public void showRandomMealErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void OnFavClickListener(Meal meal) {
        if(Constants.isLogedIn(getContext()))
            Constants.showDialog(getActivity(), LoginActivity.class);
        else
            presenter.addMealToFavourites(meal);
    }



    @Override
    public void onCategoryClickListener(String category,View view) {
        HomeFragmentDirections.ActionHomeFragmentToAllMealsFragment3 action =
                HomeFragmentDirections.actionHomeFragmentToAllMealsFragment3();
        action.setCategory(category);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void OnMealClickListener(String id,View view) {
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
        Log.i(TAG, "onMealPlanClickListener: "+plan);
        if(Constants.isLogedIn(getContext()))
            Constants.showDialog(getActivity(), LoginActivity.class);
        else
            presenter.addMealToPlan(plan);
    }

    @Override
    public void onDateSet(Calendar selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(selectedDate.getTime());
        Plan plan = new Plan(formattedDate,
                meal);
        onMealPlanClickListener(plan);
        presenter.addMealToPlanUsingFirebase(plan);
        Toast.makeText(getContext(), "Selected Date: " + formattedDate, Toast.LENGTH_SHORT).show();
    }
}