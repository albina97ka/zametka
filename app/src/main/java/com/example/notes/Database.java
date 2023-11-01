package com.example.notes;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Database {
    static Database instance;
    static SharedPreferences sharedPref;
    private Database(Activity t){
        sharedPref = t.getPreferences(Context.MODE_PRIVATE);
    }

    static Database createInstance(Activity t){
        if(instance == null){
            instance = new Database(t);
        }
        return instance;
    }

    static Boolean info(){
        if(sharedPref.contains("list")){
            return true;
        } else {
            return false;
        }
    }

    void addItem(ArrayList<Notes> list){
        Gson gson = new Gson();
        String jsonList = gson.toJson(list);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("list", jsonList);
        editor.apply();
    }

    ArrayList<Notes> getItem(String name){
        String jsonList = sharedPref.getString("listOfItem", null);
        if (jsonList != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Notes>>() {}.getType();
            ArrayList<Notes> list = gson.fromJson(jsonList, type);
            return list;
        }
        return null;
    }

    static Database getInstance(){
        return instance;
    }
}