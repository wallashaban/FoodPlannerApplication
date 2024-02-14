package com.example.foodplannerapp.network;


import com.example.foodplannerapp.models.Area;

import java.util.List;

public interface AreaNetworkCallBAck {
    public void onAreasSuccessResult(List<Area> meals);

    public void onAreasFailureResult(String errorMessage);
}
