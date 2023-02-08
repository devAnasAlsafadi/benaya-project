    package com.example.apifinalproject.model;

import com.example.apifinalproject.api.controller.ApiController;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ListenerCheckInternet;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.Types;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class Operation extends Model {


    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("actor_id")
    @Expose
    public String actorId;

    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("actor")
    @Expose
    public Resident resident;

   public String object;

    @SerializedName("admin_id")
    @Expose
    public String adminId;






    public static void getOperations(ListCallback<Operation> callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse<Operation>> call = ApiController.getInstance().getRetrofitRequest().getOperations();
                call.enqueue(new Callback<BaseResponse<Operation>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Operation>> call, Response<BaseResponse<Operation>> response) {
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

    public void saveOperations(ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().createOperations(categoryId,amount,details,actorId,object,date);
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

    public void deleteOperations(ProcessCallback callback){

        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().deleteOperations(id);
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
                        callback.onFailure("somethings went Wrong!");
                    }
                });
            }

            @Override
            public void connectionError() {
                callback.onFailure(Types.noInternet);
            }
        });

    }

    public void updateOperations(ProcessCallback callback){
        checkInternet(new ListenerCheckInternet() {
            @Override
            public void connected() {
                Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequest().updateOperations(id,categoryId,amount,details,actorId,object,date);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()&& response.body()!=null){
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
                        callback.onFailure("somethings went wrongs!");
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
