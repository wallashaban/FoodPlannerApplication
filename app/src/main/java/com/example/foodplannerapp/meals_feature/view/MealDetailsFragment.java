package com.example.foodplannerapp.meals_feature.view;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.auth_feature.view.LoginActivity;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepositoryImpl;
import com.example.foodplannerapp.meals_feature.presenter.CategoryPresenter;
import com.example.foodplannerapp.meals_feature.presenter.MealDetailsPresenter;
import com.example.foodplannerapp.meals_feature.presenter.MealDetailsPresenterImpl;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.MealIngredients;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MealDetailsFragment extends Fragment implements MealDetailsView,
        DatePickerDialogListener, OnMealPlanClickListener,OnFavClickListener{

    private static final String TAG = "CategoryFragment";
    MealDetailsPresenter presenter;
    LinearLayoutManager manager;
    RecyclerView recyclerView;
    MealIngredientsAdapter adapter;
    Meal meal;
    ImageView mealImage;
    TextView mealNAme;
    TextView description;
    CheckBox planButton;
    WebView video;
    Group group;
    ProgressBar progressBar;
    LottieAnimationView noInternetAnimation;
    private CheckBox favButton;
    private YouTubePlayerView youTubePlayerView;

    List<MealIngredients> ingredients;

    public MealDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = new ArrayList<>();
       presenter = MealDetailsPresenterImpl.getInstance(this,
               RepositoryImpl.getInstance(
                       RemoteDataSourceImpl.getInstance(),
                       FavouritesLocalDataSourceImpl.getInstance(getContext())
               ),
               FirebaseCrudRepositoryImpl
                       .getInstance(FirebaseRemoteDataSourceImpl.getInstance(getContext())
                       ));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        group = view.findViewById(R.id.mealDetailsGroup);
        progressBar = view.findViewById(R.id.mealDetailsProgressBar);
        noInternetAnimation = view.findViewById(R.id.no_internet_animationMealDetails);
        mealNAme = view.findViewById(R.id.mealDetailsName);
        description = view.findViewById(R.id.mealInstruction);
        mealImage = view.findViewById(R.id.mealDetailImage);
        favButton = view.findViewById(R.id.favMealDetailsButton);
        youTubePlayerView = view.findViewById(R.id.video);

        recyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        manager = new LinearLayoutManager(getContext());

        adapter = new MealIngredientsAdapter(getContext(), ingredients);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        planButton = view.findViewById(R.id.planMealDetailButton);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.isLogedIn(getContext()))
                    Constants.showDialog(getActivity(), LoginActivity.class);
                else
                    OnFavClickListener(meal);
            }
        });
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.isLogedIn(getContext()))
                    Constants.showDialog(getActivity(), LoginActivity.class);
                else
                    Constants.showDatePicker(getContext(),MealDetailsFragment.this);
            }
        });
        String id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();
        Log.i(TAG, "onViewCreated: meals  :: "+meal);
            presenter.getMealByID(id);
            mealNAme.setText("apple pie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void showData(Meal meal) {
        progressBar.setVisibility(View.INVISIBLE);
        group.setVisibility(View.VISIBLE);
        this.meal = meal;
        mealNAme.setText(meal.getMealName());
        description.setText(meal.getInstructions());
        Log.i(TAG, "showData: Name Meal"+meal.getMealName());
        getContext().getApplicationContext();
        if(requireContext()!=null) {
            Glide.with(requireContext()).load(meal.getMealThumb())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                            .error(R.drawable.ic_launcher_background))
                    .into(mealImage);
        }else{
            Log.i(TAG, "showData: NULL");
        }

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                int startIndex = meal.getVideo().indexOf("=") + 1;

                String videoId = meal.getVideo().substring(startIndex);
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        List<MealIngredients> ingredients = extractIngredients(meal);
        Log.i(TAG, "showData: Ingredients"+ingredients.get(0));
        adapter.setIngredients(ingredients);
        adapter.notifyDataSetChanged();
    }





    public static List<MealIngredients> extractIngredients(Meal meal) {
        List<MealIngredients> ingredientsList = new ArrayList<>();
        Field[] fields = meal.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                Log.i(TAG, "extractIngredients: ");
                field.setAccessible(true);
                String fieldName = field.getName();
                if (fieldName.startsWith("ingredient") ) {
                    Log.i(TAG, "extractIngredients: If");
                    String ingredientName = (String) field.get(meal);
                    String measureField = "measure" + fieldName.substring("ingredient".length());
                    Field measureFieldRef = meal.getClass().getDeclaredField(measureField);
                    measureFieldRef.setAccessible(true);
                    String measurement = (String) measureFieldRef.get(meal);
                    MealIngredients ingredient = new MealIngredients(ingredientName, measurement);
                    ingredientsList.add(ingredient);
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ingredientsList;
    }
    @Override
    public void showErrorMessage(String errorMessage) {
        if(errorMessage.equals("No internet connection"))
        {
            group.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            //Log.i(TAG, "showRandomMealErrorMessage: value"+homeGroup.getVisibility());
            noInternetAnimation.setVisibility(View.VISIBLE);
        }
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onMealPlanClickListener(Plan plan) {
        presenter.addMealToPlan(plan);
    }

    @Override
    public void OnFavClickListener(Meal meal) {
        if(Constants.isLogedIn(getContext()))
            Constants.showDialog(getActivity(), LoginActivity.class);
        else {
            presenter.addMealToFavourites(meal);
            presenter.addMealToFavouriteUsingFirebase(meal);
            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
        }
        }
}