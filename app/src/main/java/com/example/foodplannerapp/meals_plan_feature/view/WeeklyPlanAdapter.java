package com.example.foodplannerapp.meals_plan_feature.view;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.meals_feature.view.OnMealClickListener;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public class WeeklyPlanAdapter extends RecyclerView.Adapter<WeeklyPlanAdapter.ViewHolder> {

    private static final String TAG = "WeeklyPlan";
    private List<Plan> mealsPlan;

    public void setMealsPlan(List<Plan> mealsPlan) {

        this.mealsPlan = mealsPlan;
    }

    private Context context;
    private OnPlanClickListener listener;
    private OnMealClickListener mealClickListener;

    public WeeklyPlanAdapter(Context context,List<Plan>plans,OnPlanClickListener listener,
                             OnMealClickListener mealClickListener){
        this.context = context;
        this.mealsPlan = plans;
        this.listener = listener;
        this.mealClickListener = mealClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.weekly_plan_layout, parent, false);
        WeeklyPlanAdapter.ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "====OnCreateViewHolder Meal====");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plan mealPlan = mealsPlan.get(position);
        holder.mealName.setText(mealPlan.getMeal().getMealName());
        holder.date.setText(mealPlan.getDate());
        if(mealPlan.getMeal().getImageData()==null) {
            Glide.with(context).asBitmap().load(mealPlan.getMeal().getMealThumb())
                    .apply(new RequestOptions().override(200, 200)
                            .placeholder(R.drawable.ic_launcher_foreground) // don't forget the placeholder image
                            .error(R.drawable.ic_launcher_background))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            mealPlan.getMeal().setImageData(Constants.bitmapToByteArray(resource));
                            holder.mealImage.setImageBitmap(resource);
                        }
                    });
        }else{

            Log.i(TAG, "Bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    mealPlan.getMeal().getImageData(), 0, mealPlan.getMeal().getImageData().length);
            holder.mealImage.setImageBitmap(bitmap);
        }
        holder.planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlanButtonClickListener(mealPlan);
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealClickListener.OnMealClickListener(mealPlan.getMeal().getMealId(),v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsPlan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View layout;
        TextView mealName;
        ImageView mealImage;
        Button planButton;
        TextView date;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            mealName = layout.findViewById(R.id.searchName);
            mealImage = layout.findViewById(R.id.searchImage);
            planButton = layout.findViewById(R.id.planMealRandomButton);
            constraintLayout = layout.findViewById(R.id.planConstrainLayout);
            date = layout.findViewById(R.id.date);
        }
    }

}
