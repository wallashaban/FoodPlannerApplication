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

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.meals_feature.view.OnCategoryClickListener;
import com.example.foodplannerapp.models.Area;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.ImagesData;

import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Map;

public class AreaSearchAdapter extends RecyclerView.Adapter<AreaSearchAdapter.ViewHolder> {

    private Context context;
    private List<Area> areas;
    private Map<String,Integer> images;
    OnAreaClickListener listener;
    private static String TAG = "CategoryAdapter";

    public void setAreas(List<Area> areas) {
       // this.categories.clear();
        this.areas = areas;
       // categories.remove(8);
    }

    public AreaSearchAdapter(Context context, List<Area> areas, OnAreaClickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
        images = new ImagesData().getCountriesImages();
        Log.i(TAG, "AreaAdapter: ");
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
        Area area = areas.get(position);
        holder.categoryName.setText(area.getArea());
        holder.image.setImageResource(images.get(area.getArea()));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAreaClickLitener(area.getArea(),v);
            }
        });
        Log.i(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return areas.size();
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
