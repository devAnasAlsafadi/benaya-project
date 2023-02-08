package com.example.apifinalproject.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.apifinalproject.api.controller.AppController;
import com.example.apifinalproject.interfaces.ListenerCheckInternet;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class Model {


    protected static void checkInternet(ListenerCheckInternet listenerCheckInternet){
        ConnectivityManager manager = (ConnectivityManager) AppController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean state = networkInfo!=null && networkInfo.isConnectedOrConnecting();
        if (state){
            listenerCheckInternet.connected();
        }else {
            listenerCheckInternet.connectionError();
        }
    }

    protected  static String getError(byte[] bytes){
        String error = new String(bytes, StandardCharsets.UTF_8);
        try {
            JSONObject jsonObject = new JSONObject(error);
            return jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


}

