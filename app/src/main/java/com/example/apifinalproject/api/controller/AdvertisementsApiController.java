package com.example.apifinalproject.api.controller;

import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ListenerCheckInternet;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Advertisements;
import com.example.apifinalproject.model.BaseResponse;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class AdvertisementsApiController {

    private static AdvertisementsApiController instance;
    private AdvertisementsApiController(){

    }

    public static AdvertisementsApiController getInstance() {
        if (instance == null){
            instance = new AdvertisementsApiController();
        }
        return instance;
    }

    public  void getAdvertisements(ListCallback<Advertisements> callback){
        Advertisements.getAdvertisements(callback);
    }
    public void saveAdvertisements(Advertisements advertisements ,ProcessCallback callback){
        if (!advertisements.info.isEmpty() &&!advertisements.title.isEmpty()){
            if (advertisements.imageArrayByte!=null){
                advertisements.saveAdvertisementsWithoutUri(callback);
            }
            advertisements.saveAdvertisementsWithoutUri(callback);
        }else {
            callback.onFailure("Enter required data!");
        }
    }
    public void updateAdvertisements(Advertisements advertisements,ProcessCallback callback){
        if (!advertisements.info.isEmpty() &&!advertisements.title.isEmpty() &&!advertisements.method.isEmpty()) {
            if (advertisements.imageArrayByte!=null){
                advertisements.updateAdvertisements(callback);
            }
            advertisements.updateAdvertisementsWithoutUri(callback);
          } else {
            callback.onFailure("Enter required data!");
        }
        }
    public void deleteAdvertisements(int id , ProcessCallback callback){
        Advertisements advertisements = new Advertisements();
        advertisements.id = id;
        advertisements.deleteAdvertisements(callback);
    }
}
