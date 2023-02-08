package com.example.apifinalproject.api;

import com.example.apifinalproject.pref.AppSharedPreference;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSetting {

    private static Retrofit instance;
    private static final String BASE_URL="https://towers.mr-dev.tech/api/";


    private RetrofitSetting(){
    }

    public static synchronized  Retrofit getInstance() {
        if (instance == null){
            instance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(getClient())
                    .build();
        }
        return instance;
    }



    private static OkHttpClient getClient(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Accept","application/json");
                        builder.addHeader("Content-Type","application/json");
                        builder.addHeader("Authorization", AppSharedPreference.getInstance().getToken());
                        return chain.proceed(builder.build());
                    }
                })
                .build();
        return client;
    }
}
