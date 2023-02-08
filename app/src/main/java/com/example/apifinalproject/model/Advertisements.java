package com.example.apifinalproject.model;

import androidx.transition.Visibility;

import com.example.apifinalproject.api.controller.ApiController;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ListenerCheckInternet;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.Types;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Advertisements extends Model {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("info")
    @Expose
    public String info;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;

    public byte[] imageArrayByte;


    public String method= "PUT";

    public static void getAdvertisements(ListCallback<Advertisements> callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Advertisements>> call = ApiController.getInstance().getRetrofitRequest().getAdvertisements();
                call.enqueue(new Callback<BaseResponse<Advertisements>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Advertisements>> call, Response<BaseResponse<Advertisements>> response) {
                        if (response.isSuccessful() && response.body()!=null){
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
                    public void onFailure(Call<BaseResponse<Advertisements>> call, Throwable t) {
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

    public void saveAdvertisements(ProcessCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),imageArrayByte);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image","image-file",requestBody);

        RequestBody _title = RequestBody.create(MediaType.parse("String"),title);
        RequestBody _info = RequestBody.create(MediaType.parse("String"),info);

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().createAdvertisements(_title,_info,file);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
                        }
                        else {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
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
    public void saveAdvertisementsWithoutUri(ProcessCallback callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().createAdvertisementsWithoutUri(title,info);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
                        }
                        else {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
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



    public void updateAdvertisements(ProcessCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),imageArrayByte);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image","image-file",requestBody);

        RequestBody _title = RequestBody.create(MediaType.parse("String"),title);
        RequestBody _info = RequestBody.create(MediaType.parse("String"),info);
        RequestBody _method = RequestBody.create(MediaType.parse("String"),method);

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().updateAdvertisements(id,_title,_info,_method,file);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
                        }else {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
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
    public void updateAdvertisementsWithoutUri(ProcessCallback callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().updateAdvertisementsWithoutUri(id,title,info,method);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
                        }else {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
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


    public void deleteAdvertisements(ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().deleteAdvertisements(id);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
                        }else {
                            try {
                                callback.onFailure(getError(response.errorBody().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
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
