package com.example.foodplannerapp.meals_feature.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private static final String TAG = "MealApapter";
    private Context context;
    private List<Meal> meals;
    private OnMealClickListener mealClickListener;
    private OnFavClickListener favClickListener;

    public MealAdapter(Context context, List<Meal> meals,
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
        Log.i(TAG, "onBindViewHolder: Category");
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getMealName());


        if(meal.getImageData() == null) {
            Glide.with(context).asBitmap().load(meals.get(position).getMealThumb())

                    .apply(new RequestOptions().override(200, 200)
                            .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                            .error(R.drawable.ic_launcher_background))

                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            meal.setImageData(Constants.bitmapToByteArray(resource));
                            holder.mealImage.setImageBitmap(resource);
                        }
                    });
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(meal.getImageData(), 0, meal.getImageData().length);
            holder.mealImage.setImageBitmap(bitmap);
        }
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
        return meals.size();
    }

    public void setMeals(List<Meal> meals) {
        //meals.clear();
        Log.i(TAG, "setMeals: Category"+meals);
        this.meals = meals;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView mealName;
        ImageView mealImage;
        CheckBox favButton;
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
