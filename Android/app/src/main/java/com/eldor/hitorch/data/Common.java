package com.eldor.hitorch.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eldor.hitorch.model.Hotels;
import com.eldor.hitorch.model.Plan;
import com.eldor.hitorch.model.Respond;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class Common {

    public static String[] types = {"restaurant","hotel", "museum", "tour", "mosque","shop","cultural performance","traditional village","ecotourism","festival","hiking","horse riding","atm","bank","embassy","tour agency","hospital"};
    public static ArrayList<Plan> plans = new ArrayList<>();
    public static String BASE_URL = "http://fizmasoft.uz/hitorch/API/";
    public static ArrayList<Integer> destination;
    public static ArrayList<Integer> trip_types;
    public static ArrayList<Hotels> list = new ArrayList<>();
    public static Hotels commonobject = null;


    public static void init(){
        destination = new ArrayList<>();
        trip_types = new ArrayList<>();
    }

    public static void saveData(Context context, ArrayList<Plan> plan){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(plan);
        editor.putString("Objects", json);
        editor.apply();
    }

    public static ArrayList<Plan> takeData(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Objects", null);
        Type type = new TypeToken<ArrayList<Plan>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public static void clearData(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Objects");
        editor.apply();
    }

}
