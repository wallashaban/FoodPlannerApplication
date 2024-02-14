package com.example.foodplannerapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AreaObject {
    @SerializedName("meals")
    private List<Area> areas;

    public AreaObject() {
        areas = new ArrayList<>();
    }

    public List<Area> getAreas() {
        return areas;
    }
}
