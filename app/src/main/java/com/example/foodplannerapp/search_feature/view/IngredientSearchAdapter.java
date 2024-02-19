package com.example.foodplannerapp.search_feature.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.meals_feature.view.OnCategoryClickListener;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.ImagesData;
import com.example.foodplannerapp.models.Ingredient;

import java.util.List;

public class IngredientSearchAdapter extends RecyclerView.Adapter<IngredientSearchAdapter.ViewHolder> {

    private Context context;
    private List<Ingredient> ingredients;
    private int[] images;
    OnIngredientClickListener listener;
    private static String TAG = "CategoryAdapter";

    public void setIngredients(List<Ingredient> ingredients) {
        // this.categories.clear();
        this.ingredients = ingredients;
        // categories.remove(8);
    }

    public IngredientSearchAdapter(Context context, List<Ingredient> ingredients,
                                   OnIngredientClickListener listener) {
        this.context = context;
        this.ingredients = ingredients;
        this.listener = listener;
        images = new ImagesData().getData();
        Log.i(TAG, "ingredientsAdapter: ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.search_element, parent, false);
        ViewHolder holder = new ViewHolder(v);
        Log.i(TAG, "====OnCreateViewHolder====");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        String url = "https://www.themealdb.com/images/ingredients/" + ingredient.getIngredient() + ".png";

        holder.categoryName.setText(ingredient.getIngredient());
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                        .error(R.drawable.ic_launcher_background))
                .into(holder.image);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIngredientClickListener(ingredient.getIngredient(), v);
            }
        });
        Log.i(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView categoryName;
        ConstraintLayout constraintLayout;
        View layout;

        public ViewHolder(@NonNull View view) {
            super(view);
            layout = view;
            image = layout.findViewById(R.id.searchImage);
            categoryName = layout.findViewById(R.id.searchName);
            constraintLayout = layout.findViewById(R.id.searchConstrainLayout);
        }

    }
}
