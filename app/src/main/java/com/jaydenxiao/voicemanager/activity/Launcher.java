package com.jaydenxiao.voicemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.jaydenxiao.voicemanager.MainActivity;
import com.jaydenxiao.voicemanager.R;
import com.jaydenxiao.voicemanager.vest.MainVestActivity;
import com.jaydenxiao.voicemanager.vest.utils.MD5Util;
import com.jaydenxiao.voicemanager.vest.utils.MapUtil;
import com.jaydenxiao.voicemanager.vest.utils.RetrofitHelper;
import com.jaydenxiao.voicemanager.vest.utils.TypeEntity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by kekex on 2019/5/21.
 */

public class Launcher extends AppCompatActivity {

    public static Map<String, Object> getSimpleDriverInfo() {
        Map<String, Object> mapClient = new HashMap<String, Object>();
        try {
            mapClient.put("and_id", "c43e77649e51d4ce");
            mapClient.put("gaid", "8508785c-f435-44c0-875d-f3c575700197");
            mapClient.put("imei", "869157024059774");
            mapClient.put("mac", "60:83:34:FE:2E:6A");
            mapClient.put("sn", "");
            mapClient.put("model", "EVA-TL00");
            mapClient.put("brand", "HUAWEI");
            mapClient.put("release", "8.0.0");
            mapClient.put("sdk_version", "26");
            mapClient.put("is_root", "0");
            mapClient.put("imsi", "510109872719107");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapClient;
    }

    public static String getMapToString(Map<String, Object> map) {
        try {
            Gson gson = new Gson();
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getRequestSign(Map<String, Object> paraMap, String time) {
        try {
            Map<String, Object> data = MapUtil.sortMapByKey(paraMap);

            String jsonData = new Gson().toJson(data);
            String result = "zmaoniany@mjb@tao!cashcash96300" + "*|*" + jsonData + "@!@" + time;
            return MD5Util.encrypt(MD5Util.encrypt(result));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laun);

        Map<String, Object> map = new HashMap<>();
        map.put("app_type", "android");
        map.put("app_version", "62");
        map.put("app_package", "com.datura.palsy");
        map.put("channel", "app");
        map.put("guid", "b25c033ce0d5bbe3bfdc1c8a608f69ee");
        map.put("position", "");
        map.put("userid", "");
        map.put("version", "1");
        Map<String, Object> driverInfo = getSimpleDriverInfo();
        map.put("device_info", driverInfo);
        String timestamp = String.valueOf(System.currentTimeMillis());
        map.put("sign", getRequestSign(map, timestamp));
        map.put("timestamp", timestamp);
        String mapToString = getMapToString(map);
        Log.e("xuke", "--->" + mapToString);
        Call<TypeEntity> type = RetrofitHelper.getInstance().getType(mapToString);
        type.enqueue(new Callback<TypeEntity>() {
            @Override
            public void onResponse(Call<TypeEntity> call, Response<TypeEntity> response) {
                TypeEntity body = response.body();
                if (body != null) {
                    Log.e("xuke", "---");
                    TypeEntity.DataBean data = body.getData();

                    if (data != null) {
                        int status = data.getStatus();
                        Log.e("xuke","status---"+status);
                        if (status == 0) {
                            startActivity(new Intent(Launcher.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(Launcher.this, MainVestActivity.class));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TypeEntity> call, Throwable t) {
                Log.e("xuke", t.getMessage());
                startActivity(new Intent(Launcher.this, MainActivity.class));
            }
        });

    }
}


