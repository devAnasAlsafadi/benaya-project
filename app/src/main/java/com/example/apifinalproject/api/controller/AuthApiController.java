package com.example.apifinalproject.api.controller;

import com.example.apifinalproject.interfaces.ListenerCheckInternet;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Admin;
import com.example.apifinalproject.model.BaseResponse;
import com.example.apifinalproject.model.Model;
import com.example.apifinalproject.model.Resident;
import com.example.apifinalproject.pref.AppSharedPreference;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthApiController extends Model {

    private static  AuthApiController instance;
    private AuthApiController(){

    }

    public static synchronized AuthApiController getInstance() {
        if (instance == null){
            instance = new AuthApiController();
        }
        return instance;
    }

    public void login(String email, String pass, ProcessCallback callback) {
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Admin>> call = ApiController.getInstance().getRetrofitRequest().login(email, pass);
                call.enqueue(new Callback<BaseResponse<Admin>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Admin>> call, Response<BaseResponse<Admin>> response) {
                        if (response.isSuccessful()&&response.body()!=null){
                            AppSharedPreference.getInstance().saveResident(response.body().data);
                            AppSharedPreference.getInstance().saveActorType(response.body().type);
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
                    public void onFailure(Call<BaseResponse<Admin>> call, Throwable t) {
                        callback.onFailure("somethings went wrong!");
                    }
                });
            }

            @Override
            public void connectionError() {

                callback.onFailure("no internet connection !");
            }
        });

    }

    public void logout(ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().logout();
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.code() == 200 || response.code() == 401){
                            AppSharedPreference.getInstance().clear();
                            callback.onSuccess(response.code()==200?response.body().message:"Logout Successfully");
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
                        callback.onFailure("somethings went Wrong!");
                    }
                });
            }

            @Override
            public void connectionError() {
                callback.onFailure("no internet connection !");
            }
        });

    }


    public void forgetPassword(String email , ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().forgetPassword(email);
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
                callback.onFailure("no Internet connections!");
            }
        });
    }
    public void resetPassword(String email ,String code ,String password , String password_confirmation,  ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().resetPassword(email,code,password,password_confirmation);
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
                callback.onFailure("no Internet connections!");
            }
        });
    }
    public void changePassword(String current_password ,String new_password ,String new_password_confirmation, ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().changePassword(current_password, new_password, new_password_confirmation);
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
                callback.onFailure("no Internet connections!");
            }
        });
    }


}
