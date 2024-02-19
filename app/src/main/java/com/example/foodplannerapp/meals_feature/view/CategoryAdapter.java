package com.example.foodplannerapp.meals_feature.view;

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

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.ImagesData;

import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;
    private Map<String,Integer> images;
    OnCategoryClickListener listener;
    private static String TAG = "CategoryAdapter";

    public void setCategories(List<Category> categories) {
       // this.categories.clear();
        this.categories = categories;
       // categories.remove(8);
    }

    public CategoryAdapter(Context context, List<Category> categories,OnCategoryClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
        images = new ImagesData().getCategoriesImages();
        Log.i(TAG, "CategoryAdapter: ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.category_element, parent, false);
        ViewHolder holder = new ViewHolder(v);
        Log.i(TAG, "====OnCreateViewHolder====");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getCategory());
        //Log.i(TAG, "onBindViewHolder: Data Category "+data.get(R.id.categories));
        holder.image.setImageResource(images.get(category.getCategory()));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClickListener(category.getCategory(),v);
            }
        });
        Log.i(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView categoryName;
        ConstraintLayout constraintLayout;
        View layout;

        public ViewHolder(@NonNull View view) {
            super(view);
            layout = view;
            image = layout.findViewById(R.id.categoryImage);
            categoryName = layout.findViewById(R.id.categoryName);
            constraintLayout = layout.findViewById(R.id.categoryConstrainLayout);
        }

    }
}
