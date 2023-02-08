package com.example.apifinalproject.model;

import com.example.apifinalproject.api.controller.ApiController;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ListenerCheckInternet;
import com.example.apifinalproject.interfaces.Types;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends Model {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("actions_count")
    @Expose
    public String actionsCount;
    @SerializedName("total")
    @Expose
    public Integer total;


    public static void getCategory(ListCallback<Category> callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Category>> call = ApiController.getInstance().getRetrofitRequest().getCategory();
                call.enqueue(new Callback<BaseResponse<Category>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Category>> call, Response<BaseResponse<Category>> response) {
                        if (response.isSuccessful()&&response.body()!=null){
                            callback.onSuccess(response.body().list);
                        }else {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Category>> call, Throwable t) {
                        callback.onFailure("somethings went wrong!");
                    }
                });
            }

            @Override
            public void connectionError() {
                callback.onFailure(Types.noInternet);
            }
        });

    }

    public  void getOperationsByCategoryId( ListCallback<Operation> callback) {
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Operation>> call = ApiController.getInstance().getRetrofitRequest().getCategoryById(String.valueOf(id));
                call.enqueue(new Callback<BaseResponse<Operation>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Operation>> call, Response<BaseResponse<Operation>> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().list);
                        }else
                        {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Operation>> call, Throwable t) {
                        callback.onFailure("somethings went wrong!");
                    }
                });

            }

            @Override
            public void connectionError() {
                callback.onFailure(Types.noInternet);
            }
        });

    }

}
