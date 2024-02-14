package com.example.foodplannerapp.meals_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.meals_feature.presenter.CategoryPresenter;
import com.example.foodplannerapp.meals_feature.presenter.CategoryPresenterImpl;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryView,OnCategoryClickListener{


    private static final String TAG = "CategoryFragment";
    CategoryPresenter presenter;
    LinearLayoutManager categoryManager;
    RecyclerView categoeryRecyclerView;
    CategoryAdapter categoryAdapter;
    List<Category> categories;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        presenter = CategoryPresenterImpl.getInstance(this, RepositoryImpl.getInstance(
                RemoteDataSourceImpl.getInstance(),
                FavouritesLocalDataSourceImpl.getInstance(getContext())
        ));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoeryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        categoryManager = new LinearLayoutManager(getContext());

        categoryAdapter = new CategoryAdapter(getContext(), categories,this);
        categoryManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        categoeryRecyclerView.setLayoutManager(categoryManager);
        categoeryRecyclerView.setAdapter(categoryAdapter);
        Log.i(TAG, "onViewCreated: meals  :: "+categories.size());
        if (categories.isEmpty())
            presenter.getAllCategories();
    }

    @Override
    public void showData(List<Category> categories) {
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClickListener(String category, View view) {

        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mealDetailsFragment2);

//        CategoryFragmentDirections action =
//                HomeFragmentDirections.actionHomeFragmentToAllMealsFragment();
//        action.setCategory(category);
//        Navigation.findNavController(view).navigate(action);
    }
}