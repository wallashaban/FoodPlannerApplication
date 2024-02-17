package com.example.foodplannerapp.models;

import com.example.foodplannerapp.search_feature.view.AreaSearchAdapter;
import com.example.foodplannerapp.search_feature.view.CategorySearchAdapter;

import java.util.List;

public class AreaSearchParameters {
    List<Area> areas;
    List<Area> filteredAreea;

    public List<Area> getAreas() {
        return areas;
    }

    public List<Area> getFilteredAreea() {
        return filteredAreea;
    }

    public AreaSearchAdapter getAdapter() {
        return adapter;
    }

    public CharSequence getSequence() {
        return sequence;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public void setFilteredAreea(List<Area> filteredAreea) {
        this.filteredAreea = filteredAreea;
    }

    public void setAdapter(AreaSearchAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSequence(CharSequence sequence) {
        this.sequence = sequence;
    }

    public AreaSearchParameters(List<Area> areas, List<Area> filteredAreea, AreaSearchAdapter adapter, CharSequence sequence) {
        this.areas = areas;
        this.filteredAreea = filteredAreea;
        this.adapter = adapter;
        this.sequence = sequence;
    }

    AreaSearchAdapter adapter;
    CharSequence sequence;


}
