
package com.example.apifinalproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.http.Field;

public class Admin  {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("tower_name")
    @Expose
    public String towerName;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;

    @SerializedName("reset_code")
    @Expose
    public String reset_code;

    public String method;
    public String password;

}
