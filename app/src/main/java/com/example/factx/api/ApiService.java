package com.example.factx.api;

import com.example.factx.model.RegisterRequest;
import com.example.factx.model.RegisterResponse;

import com.example.factx.model.VerifyOTPRequest;
import com.example.factx.model.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import com.example.factx.model.LoginRequest;
import com.example.factx.model.LoginResponse;

import com.example.factx.model.ProfileResponse;
import com.example.factx.model.ProfileUpdateRequest;

import com.example.factx.model.TextAnalysisRequest;
import com.example.factx.model.TextAnalysisResponse;

import com.example.factx.model.ForgotPasswordRequest;
import com.example.factx.model.ForgotPasswordResponse;
import com.example.factx.model.ResetPasswordRequest;
import com.example.factx.model.ResetPasswordResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @POST("register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @POST("verify")
    Call<VerifyOTPResponse> verifyOtp(@Body VerifyOTPRequest request);

    @GET("profile/{email}")
    Call<ProfileResponse> getProfile(@Path("email") String email);

    @PUT("profile")
    Call<RegisterResponse> updateProfile(@Body ProfileUpdateRequest request);

    @POST("text-analysis")
    Call<TextAnalysisResponse> analyzeText(
            @Body TextAnalysisRequest request
    );
    @POST("forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(
            @Body ForgotPasswordRequest request
    );

    @POST("reset-password")
    Call<ResetPasswordResponse> resetPassword(
            @Body ResetPasswordRequest request
    );



}

