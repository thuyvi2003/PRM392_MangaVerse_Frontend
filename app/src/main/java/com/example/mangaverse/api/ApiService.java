package com.example.mangaverse.api;

import com.example.mangaverse.model.auth.AuthResponse;
import com.example.mangaverse.model.auth.LoginRequest;
import com.example.mangaverse.model.auth.RegisterRequest;
import com.example.mangaverse.model.auth.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    // 1. POST /api/auth/register
    @POST("api/auth/register")
    Call<AuthResponse> registerUser(@Body RegisterRequest userData);

    // 2. POST /api/auth/login
    @POST("api/auth/login")
    Call<AuthResponse> loginUser(@Body LoginRequest loginData);

    // 3. GET /api/profile/me
    @GET("api/profile/me")
    Call<UserModel> getProfile(@Header("Authorization") String authToken);

    // 4. PUT /api/profile
    @PUT("api/profile")
    Call<UserModel> updateProfile(
            @Header("Authorization") String authToken,
            @Body UserModel updateData
    );
}