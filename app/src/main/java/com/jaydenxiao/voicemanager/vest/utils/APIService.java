package com.jaydenxiao.voicemanager.vest.utils;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("tools-calculator/type-status")
    Call<TypeEntity> getType(
            @Field("data") String data
    );

}