package com.example.apifinalproject.api.controller;


import com.example.apifinalproject.api.RetrofitRequest;
import com.example.apifinalproject.api.RetrofitSetting;

public class ApiController {

    private RetrofitRequest retrofitRequest;
    private static ApiController instance;

    private ApiController(){
        retrofitRequest = RetrofitSetting.getInstance().create(RetrofitRequest.class);
    }

    public static ApiController getInstance() {
        if (instance == null){
            instance = new ApiController();
        }
        return instance;
    }

    public RetrofitRequest getRetrofitRequest() {
        return retrofitRequest;
    }
}
