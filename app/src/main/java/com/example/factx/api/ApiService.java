package com.example.factx.api;

import com.example.factx.model.ForgotPasswordRequest;
import com.example.factx.model.ForgotPasswordResponse;
import com.example.factx.model.LoginRequest;
import com.example.factx.model.LoginResponse;
import com.example.factx.model.ProfileResponse;
import com.example.factx.model.ProfileUpdateRequest;
import com.example.factx.model.RegisterRequest;
import com.example.factx.model.RegisterResponse;
import com.example.factx.model.ResetPasswordRequest;
import com.example.factx.model.ResetPasswordResponse;
import com.example.factx.model.TextAnalysisRequest;
import com.example.factx.model.TextAnalysisResponse;
import com.example.factx.model.VerifyOTPRequest;
import com.example.factx.model.VerifyOTPResponse;
import com.example.factx.model.VerifyResetOTPRequest;
import com.example.factx.model.VerifyResetOTPResponse;
import com.example.factx.model.ReportRequest;
import com.example.factx.model.ReportResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // =====================================================
    // REGISTER
    // =====================================================

    @POST("register")
    Call<RegisterResponse> registerUser(
            @Body RegisterRequest request
    );


    // =====================================================
    // LOGIN
    // =====================================================

    @POST("login")
    Call<LoginResponse> loginUser(
            @Body LoginRequest request
    );


    // =====================================================
    // VERIFY REGISTRATION OTP
    // =====================================================

    @POST("verify")
    Call<VerifyOTPResponse> verifyOtp(
            @Body VerifyOTPRequest request
    );


    // =====================================================
    // GET PROFILE
    // =====================================================

    @GET("profile/{email}")
    Call<ProfileResponse> getProfile(
            @Path("email") String email
    );


    // =====================================================
    // UPDATE PROFILE
    // =====================================================

    @PUT("profile")
    Call<RegisterResponse> updateProfile(
            @Body ProfileUpdateRequest request
    );


    // =====================================================
    // TEXT ANALYSIS
    // =====================================================

    @POST("text-analysis")
    Call<TextAnalysisResponse> analyzeText(
            @Body TextAnalysisRequest request
    );


    // =====================================================
    // FORGOT PASSWORD - SEND OTP
    // =====================================================

    @POST("forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(
            @Body ForgotPasswordRequest request
    );


    // =====================================================
    // VERIFY RESET PASSWORD OTP
    // =====================================================

    @POST("verify-reset-otp")
    Call<VerifyResetOTPResponse> verifyResetOtp(
            @Body VerifyResetOTPRequest request
    );


    // =====================================================
    // RESET PASSWORD
    // =====================================================

    @POST("reset-password")
    Call<ResetPasswordResponse> resetPassword(
            @Body ResetPasswordRequest request
    );


    // =====================================================
    // REPORT PROBLEM
    // =====================================================

    @POST("report")
    Call<ReportResponse> submitReport(
            @Body ReportRequest request
    );
}