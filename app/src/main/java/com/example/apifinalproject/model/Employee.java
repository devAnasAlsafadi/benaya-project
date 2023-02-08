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

public class Employee extends Model  implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("national_number")
    @Expose
    public String nationalNumber;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;

    public String method ="PUT";


    public byte[] imageArrayByte;



    public void createEmployee(ProcessCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),imageArrayByte);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image","image-file",requestBody);

        RequestBody _name = RequestBody.create(MediaType.parse("String"),name);
        RequestBody _mobile = RequestBody.create(MediaType.parse("String"),mobile);
        RequestBody _nationalNumber = RequestBody.create(MediaType.parse("String"),nationalNumber);

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().createEmployee(_name,_mobile,_nationalNumber,file);
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

    public void deleteEmployee( ProcessCallback callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().deleteEmployee(id);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            callback.onSuccess(response.body().message);
                        }else{
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

    public void updateEmployee(ProcessCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),imageArrayByte);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image","image-file",requestBody);

        RequestBody _name = RequestBody.create(MediaType.parse("String"),name);
        RequestBody _mobile = RequestBody.create(MediaType.parse("String"),mobile);
        RequestBody _nationalNumber = RequestBody.create(MediaType.parse("String"),nationalNumber);
        RequestBody _method = RequestBody.create(MediaType.parse("String"),method);

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().updateEmployee(id,_name,_mobile,_nationalNumber,file,_method);
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

    public static void getEmployees(ListCallback<Employee> callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Employee>> call = ApiController.getInstance().getRetrofitRequest().getEmployee();
                call.enqueue(new Callback<BaseResponse<Employee>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {
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
                    public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {
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
