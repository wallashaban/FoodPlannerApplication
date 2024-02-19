package com.example.foodplannerapp.meals_feature.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.MealIngredients;

import java.util.List;

public class MealIngredientsAdapter extends RecyclerView.Adapter<MealIngredientsAdapter.ViewHolder> {

    private static final String TAG = "MealIngredientsAdapter";
    private List<MealIngredients> ingredients;
    private Context context;

    public void setIngredients(List<MealIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public MealIngredientsAdapter(Context context, List<MealIngredients> ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.meal_ingredient_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        Log.i(TAG, "====OnCreateViewHolder====");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealIngredients ingredient = ingredients.get(position);
        String url = "https://www.themealdb.com/images/ingredients/" + ingredient.getName() + ".png";

        if (ingredient.getName() != null && ingredient.getMeasurement() != null
                && !ingredient.getName().isEmpty() && !ingredient.getMeasurement().isEmpty()) {
            holder.ingredient.setText(ingredient.getName());

            Glide.with(context).load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                            .error(R.drawable.ic_launcher_background))
                    .into(holder.image);
            holder.measure.setText(ingredient.getMeasurement());
        }
        Log.i(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient;
        TextView measure;
        ImageView image;

        View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            ingredient = layout.findViewById(R.id.ingredient);
            measure = layout.findViewById(R.id.measure);
            image = layout.findViewById(R.id.ingredientImage);
        }
    }
}
