package com.example.factx.api;

import com.example.factx.model.RegisterRequest;
import com.example.factx.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

}