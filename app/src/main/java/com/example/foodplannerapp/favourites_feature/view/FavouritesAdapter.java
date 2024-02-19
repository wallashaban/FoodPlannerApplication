package com.example.foodplannerapp.favourites_feature.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.meals_feature.view.OnFavClickListener;
import com.example.foodplannerapp.meals_feature.view.OnMealClickListener;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private static final String TAG = "MealApapter";
    private Context context;
    private List<Meal> meals;
    private OnMealClickListener mealClickListener;
    private OnFavClickListener favClickListener;

    public FavouritesAdapter(Context context, List<Meal> meals,
                             OnMealClickListener mealClickListener,
                             OnFavClickListener favClickListener) {
        this.context = context;
        this.meals = meals;
        this.mealClickListener = mealClickListener;
        this.favClickListener = favClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.meal_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "====OnCreateViewHolder Meal====");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getMealName());
        Glide.with(context).load(meals.get(position).getMealThumb())
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                        .error(R.drawable.ic_launcher_background))
                .into(holder.mealImage);

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favClickListener.OnFavClickListener(meal);
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealClickListener.OnMealClickListener(meal.getMealId(),v);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+meals.size());
        return meals.size();
    }

    public void setMeals(List<Meal> meals) {
        this.meals.clear();
        this.meals = meals;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        TextView mealName;
        ImageView mealImage;
        Button favButton;
        ConstraintLayout constraintLayout;


        public ViewHolder(View layout) {
            super(layout);
            this.layout = layout;
            mealName = layout.findViewById(R.id.searchName);
            mealImage = layout.findViewById(R.id.searchImage);
            favButton = layout.findViewById(R.id.favButton);
            constraintLayout = layout.findViewById(R.id.planConstrainLayout);
        }
    }
}
