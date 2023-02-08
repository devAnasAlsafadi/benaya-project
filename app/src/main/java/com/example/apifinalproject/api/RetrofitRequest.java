package com.example.apifinalproject.api;



import com.example.apifinalproject.model.Admin;
import com.example.apifinalproject.model.Advertisements;
import com.example.apifinalproject.model.BaseResponse;
import com.example.apifinalproject.model.Category;
import com.example.apifinalproject.model.Employee;
import com.example.apifinalproject.model.Operation;
import com.example.apifinalproject.model.Resident;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitRequest {



    @FormUrlEncoded
    @POST("auth/login")
    Call<BaseResponse<Admin>> login(@Field("email") String email , @Field("password") String password);



    @FormUrlEncoded
    @POST("auth/forget-password")
    Call<BaseResponse> forgetPassword(@Field("email") String email);





    @FormUrlEncoded
    @POST("auth/reset-password")
    Call<BaseResponse> resetPassword(@Field("email") String email , @Field("code") String code , @Field("password") String password , @Field("password_confirmation") String password_confirmation);


    @FormUrlEncoded
    @POST("auth/change-password")
    Call<BaseResponse> changePassword(@Field("current_password") String current_password , @Field("new_password") String new_password , @Field("new_password_confirmation") String  new_password_confirmation);



    @GET("auth/logout")
    Call<BaseResponse> logout();

    //*****************************


    @GET("employees")
    Call<BaseResponse<Employee>> getEmployee();


    @Multipart
    @POST("employees")
    Call<BaseResponse> createEmployee(@Part("name") RequestBody name
            ,@Part("mobile") RequestBody mobile
            ,@Part("national_number") RequestBody national_number
            ,@Part MultipartBody.Part image);



    @DELETE("employees/{id}")
    Call<BaseResponse> deleteEmployee(@Path("id") int id);



    @Multipart
    @POST("employees/{id}")
    Call<BaseResponse> updateEmployee(@Path("id") int  id ,
                                      @Part("name") RequestBody name ,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("national_number") RequestBody national_number
                                     ,@Part MultipartBody.Part image
                                     ,@Part("_method") RequestBody method);


    //***************



    @GET("users")
    Call<BaseResponse<Resident>> getResident();


    @Multipart
    @POST("users")
    Call<BaseResponse> createResident(@Part("name") RequestBody name
                                     ,@Part("email") RequestBody email,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("national_number") RequestBody national_number,
                                      @Part("family_members") RequestBody family_members,
                                      @Part("gender") RequestBody gender,
                                      @Part MultipartBody.Part image);



    @DELETE("users/{id}")
    Call<BaseResponse> deleteResident(@Path("id") int id);


    @Multipart
    @POST("users/{id}")
    Call<BaseResponse<Resident>> updateResident(@Path("id") int  id
            ,@Part("name") RequestBody name
            ,@Part("email") RequestBody email
            ,@Part("mobile") RequestBody mobile
            ,@Part("national_number") RequestBody national_number
            ,@Part("family_members") RequestBody family_members
            ,@Part("gender") RequestBody gender
            ,@Part() MultipartBody.Part image
            ,@Part("_method") RequestBody method);

    //********************


    @GET("categories")
    Call<BaseResponse<Category>>  getCategory();

    @GET("categories/{id}")
    Call<BaseResponse<Operation>> getCategoryById(@Path("id") String id);

    //********************


    @GET("operations")
    Call<BaseResponse<Operation>> getOperations();


    @FormUrlEncoded
    @POST("operations")
    Call<BaseResponse> createOperations(@Field("category_id") String category_id
            , @Field("amount") String amount
            , @Field("details") String details
            , @Field("actor_id") String actor_id
            , @Field("actor_type") String object
            , @Field("date") String date );

    @FormUrlEncoded
    @PUT("operations/{id}")
    Call<BaseResponse> updateOperations(@Path("id") int id
            ,@Field("category_id") String category_id
            , @Field("amount") String amount
            , @Field("details") String details
            , @Field("actor_id") String actor_id
            , @Field("actor_type") String object
            , @Field("date") String date);


    @DELETE("operations/{id}")
    Call<BaseResponse> deleteOperations(@Path("id") int id);


   //********************



    @GET("advertisements")
    Call<BaseResponse<Advertisements>> getAdvertisements();



    @Multipart
    @POST("advertisements")
    Call<BaseResponse> createAdvertisements(@Part("title") RequestBody title
            , @Part("info") RequestBody info
            ,@Part MultipartBody.Part image );


    @FormUrlEncoded
    @POST("advertisements")
    Call<BaseResponse> createAdvertisementsWithoutUri(@Field("title") String  title
            , @Field("info") String info);



    @Multipart
    @PUT("advertisements/{id}")
    Call<BaseResponse> updateAdvertisements(@Path("id") int id
            ,@Part("title") RequestBody title
            , @Part("info") RequestBody info
            , @Part("_method") RequestBody _method
            ,@Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @PUT("advertisements/{id}")
    Call<BaseResponse> updateAdvertisementsWithoutUri(@Path("id") int id
            ,@Field("title") String title
            , @Field("info") String info
            , @Field("_method") String _method
    );


    @DELETE("advertisements/{id}")
    Call<BaseResponse> deleteAdvertisements(@Path("id") int id);



}
