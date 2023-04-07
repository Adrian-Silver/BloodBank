package com.example.bloodbank2_0_main.mpesa.interceptor;

import android.util.Base64;

import androidx.annotation.NonNull;

import com.example.bloodbank2_0_main.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {
    public AccessTokenInterceptor() {

    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        String keys = BuildConfig.DARAJA_CONSUMER_KEY + ":" + BuildConfig.DARAJA_CONSUMER_SECRET;

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic " + Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP))
                .build();
        return chain.proceed(request);
    }
}
