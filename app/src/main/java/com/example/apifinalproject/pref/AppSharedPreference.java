package com.example.apifinalproject.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.apifinalproject.api.controller.AppController;
import com.example.apifinalproject.enums.prefKeysAdmin;
import com.example.apifinalproject.model.Admin;


public class AppSharedPreference {

    public SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;

    private AppSharedPreference() {
        sharedPreferences = AppController.getInstance().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private static AppSharedPreference instance;



    public static AppSharedPreference getInstance() {
        if (instance == null) {
            instance = new AppSharedPreference();
        }

        return instance;
    }



    public void saveActorType(String type){
        editor.putString(prefKeysAdmin.actor_type.name(),type);
        editor.apply();
    }

    public String getActorType(){
        return sharedPreferences.getString(prefKeysAdmin.actor_type.name(),"");
    }


    public void saveResident(Admin admin) {
        editor.putBoolean(prefKeysAdmin.loggedIn.name(), true);
        editor.putInt(prefKeysAdmin.id.name(), admin.id);
        editor.putString(prefKeysAdmin.name.name(), admin.name);
        editor.putString(prefKeysAdmin.email.name(), admin.email);
        editor.putString(prefKeysAdmin.tower_name.name(), admin.towerName);
        editor.putString(prefKeysAdmin.token.name(), "Bearer " + admin.token);
        editor.apply();
    }


    public String getToken() {
        return sharedPreferences.getString(prefKeysAdmin.token.name(), "");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(prefKeysAdmin.loggedIn.name(), false);
    }


    public void clear() {
        editor.clear();
        editor.apply();
    }
}
