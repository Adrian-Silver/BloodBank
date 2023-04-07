package com.example.bloodbank2_0_main.mpesa.services;

import com.example.bloodbank2_0_main.mpesa.model.AccessToken;
import com.example.bloodbank2_0_main.mpesa.model.STKPush;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface STKPushService {

    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPush> sendPush(@Body STKPush stkPush);

    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken();
}
