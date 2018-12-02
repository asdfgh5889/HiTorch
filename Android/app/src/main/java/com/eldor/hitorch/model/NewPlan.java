package com.eldor.hitorch.model;

import com.google.gson.Gson;

public class NewPlan {

    private Integer[] regions;
    private Integer[] types;
    private int time;
    private int cost;

    public NewPlan(Integer[] regions, Integer[] types, int time, int cost) {
        this.regions = regions;
        this.types = types;
        this.time = time;
        this.cost = cost;
    }

    public String convertToJson(NewPlan newPlan){
        Gson gson = new Gson();

        return gson.toJson(newPlan);
    }
}
