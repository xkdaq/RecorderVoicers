package com.jaydenxiao.voicemanager.vest.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static final long DEFAULT_TIMEOUT = 30;
    private static RetrofitHelper mInstance;
    private APIService mAPIService;
    private static Gson gson;


    private RetrofitHelper() {
        initRetrofit();
    }

    public static RetrofitHelper getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitHelper();
                    gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                }
            }
        }
        return mInstance;
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://apis.pisopiso.co.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getBuilder().build())
                .build();
        mAPIService = retrofit.create(APIService.class);
    }

    private OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder;
    }


    public Call<TypeEntity> getType(String data) {
        return mAPIService.getType(data);
    }


}
