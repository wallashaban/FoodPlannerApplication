package com.example.foodplannerapp.meals_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSourceImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepositoryImpl;
import com.example.foodplannerapp.meals_feature.presenter.RandomMealPresenter;
import com.example.foodplannerapp.meals_feature.presenter.RandomMealPresenterImpl;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;


public class RandomMealFragment extends Fragment implements RandomMealView,OnFavClickListener{


    private static final String LOADINGFRAGMENT = "loading";
    private FragmentManager fragmentManager;
    private RandomMealPresenter presenter;
   private static String TAG = "RandomMealFragment";

    private ImageButton favButton;
    private ImageView mealImage;
    Group mealGroup;
    private ConstraintLayout constraintLayout;
    private TextView mealName;
    private Meal meal;
    private ProgressBar progress;


    public RandomMealFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = RandomMealPresenterImpl.getInstance(this,
                RepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        FavouritesLocalDataSourceImpl.getInstance(getContext())
                ),
                FirebaseCrudRepositoryImpl
                        .getInstance(FirebaseRemoteDataSourceImpl.getInstance(getContext()))
                        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: infater");
        return inflater.inflate(R.layout.fragment_random_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealName = view.findViewById(R.id.searchName);
        mealImage = view.findViewById(R.id.searchImage);
        favButton = view.findViewById(R.id.favButton);
        progress = view.findViewById(R.id.progress);
        mealGroup = view.findViewById(R.id.mealGroup);
        //mealGroup.setVisibility(View.INVISIBLE);
        if(meal == null)
            presenter.getRandomMeal();
        else{
            mealName.setText(meal.getMealName());
            Glide.with(getContext()).load(meal.getMealThumb())
                    .apply(new RequestOptions().override(200, 200)
                            .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                            .error(R.drawable.ic_launcher_background))
                    .into(mealImage);
            mealGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void OnFavClickListener(Meal meal) {
        presenter.addRandomMealToFavourites(meal);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LiveData<Meal> favMeal = presenter.getFavMealById(meal.getMealId());
//                Log.i(TAG, "onClick: favMeal "+ favMeal.getValue());
//                if(favMeal == null)
//                {
//                    Log.i(TAG, "onClick: If con");
//                    presenter.addMealToFavourites(meal);
//                    favButton.setHighlightColor(ContextCompat.getColor(getContext(), R.color.white));
//                }else{
//                    Log.i(TAG, "onClick: else con");
//                    presenter.removeMealFromFavourites(meal);
//                    favButton.setHighlightColor(ContextCompat.getColor(getContext(), R.color.black));
//                }
//
//            }
//        });

    }


    @Override
    public void showData(Meal meal) {
        this.meal = meal;
        Glide.with(getContext()).load(meal.getMealThumb())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                        .error(R.drawable.ic_launcher_background))
                .into(mealImage);


        progress.setVisibility(View.INVISIBLE);
        mealGroup.setVisibility(View.VISIBLE);
        mealName.setText(meal.getMealName());


    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}