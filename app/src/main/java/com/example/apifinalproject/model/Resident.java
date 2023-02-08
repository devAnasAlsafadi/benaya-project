package com.example.apifinalproject.model;

import com.example.apifinalproject.api.controller.ApiController;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ListenerCheckInternet;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.Types;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resident extends Model implements Serializable {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("national_number")
    @Expose
    public String nationalNumber;
    @SerializedName("family_members")
    @Expose
    public String familyMembers;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("admin_id")
    @Expose
    public Integer adminId;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;

    @SerializedName("tower_name")
    @Expose
    public String tower_name;


    @SerializedName("refresh_token")
    @Expose
    public String refresh_token;


    @SerializedName("token_type")
    @Expose
    public String token_type;

    @SerializedName("token")
    @Expose
    public String token;

    public String method = "PUT";

    public byte[] imageArrayByte;



    public void createResident(ProcessCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),imageArrayByte);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image","image-file",requestBody);
        RequestBody _name = RequestBody.create(MediaType.parse("String"),name);
        RequestBody _email = RequestBody.create(MediaType.parse("String"),email);
        RequestBody _mobile = RequestBody.create(MediaType.parse("String"),mobile);
        RequestBody _nationalNumber = RequestBody.create(MediaType.parse("String"),nationalNumber);
        RequestBody _familyMembers = RequestBody.create(MediaType.parse("String"),familyMembers);
        RequestBody _gender = RequestBody.create(MediaType.parse("String"),gender);

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().createResident(_name,_email,_mobile,_nationalNumber,_familyMembers,_gender,file);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
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

    public void updateResident( ProcessCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),imageArrayByte);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image","image-file",requestBody);
        RequestBody _name = RequestBody.create(MediaType.parse("String"),name);
        RequestBody _email = RequestBody.create(MediaType.parse("String"),email);
        RequestBody _mobile = RequestBody.create(MediaType.parse("String"),mobile);
        RequestBody _nationalNumber = RequestBody.create(MediaType.parse("String"),nationalNumber);
        RequestBody _familyMembers = RequestBody.create(MediaType.parse("String"),familyMembers);
        RequestBody _gender = RequestBody.create(MediaType.parse("String"),gender);
        RequestBody _method = RequestBody.create(MediaType.parse("String"),method);

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Resident>> call = ApiController.getInstance().getRetrofitRequest().updateResident(id,_name,_email,_mobile,_nationalNumber,_familyMembers,_gender,file,_method);
                call.enqueue(new Callback<BaseResponse<Resident>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Resident>> call, Response<BaseResponse<Resident>> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
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
                    public void onFailure(Call<BaseResponse<Resident>> call, Throwable t) {
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

    public void deleteResident(ProcessCallback callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().deleteResident(id);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
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

    public static void getResident(ListCallback<Resident> callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Resident>> call = ApiController.getInstance().getRetrofitRequest().getResident();
                call.enqueue(new Callback<BaseResponse<Resident>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Resident>> call, Response<BaseResponse<Resident>> response) {
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
                    public void onFailure(Call<BaseResponse<Resident>> call, Throwable t) {
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
