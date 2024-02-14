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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
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

public class MealDetailsFragment extends Fragment implements MealDetailsView,
        DatePickerDialogListener, OnMealPlanClickListener{

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
               ));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealNAme = view.findViewById(R.id.mealDetailsName);
        description = view.findViewById(R.id.mealInstruction);
        mealImage = view.findViewById(R.id.mealDetailImage);
        youTubePlayerView = view.findViewById(R.id.video);

        recyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        manager = new LinearLayoutManager(getContext());

        adapter = new MealIngredientsAdapter(getContext(), ingredients);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        planButton = view.findViewById(R.id.planMealDetailButton);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void showData(Meal meal) {
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
                String videoId = "qzcGfN9S_QY";
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
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(Calendar selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(selectedDate.getTime());
        Plan plan = new Plan(formattedDate,
                meal);
        onMealPlanClickListener(plan);
    }

    @Override
    public void onMealPlanClickListener(Plan plan) {
        presenter.addMealToPlan(plan);
    }
}