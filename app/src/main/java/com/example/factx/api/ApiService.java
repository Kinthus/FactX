package com.example.factx.api;

import com.example.factx.model.RegisterRequest;
import com.example.factx.model.RegisterResponse;

import com.example.factx.model.VerifyOTPRequest;
import com.example.factx.model.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import com.example.factx.model.LoginRequest;
import com.example.factx.model.LoginResponse;

public interface ApiService {

    @POST("register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @POST("verify")
    Call<VerifyOTPResponse> verifyOtp(@Body VerifyOTPRequest request);
}

