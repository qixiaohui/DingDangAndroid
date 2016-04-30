package com.qi.xiaohui.dingdang.dao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qixiaohui on 4/4/16.
 */
public class RestClient {
    static final String baseUrl = "http://10.0.0.3:2000/";

    public static Gateway getGateway(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Gateway.class);
    }
}
