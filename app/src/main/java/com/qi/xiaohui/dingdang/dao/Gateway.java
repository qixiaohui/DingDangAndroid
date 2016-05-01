package com.qi.xiaohui.dingdang.dao;

import com.qi.xiaohui.dingdang.model.webcontent.WebContent;
import com.qi.xiaohui.dingdang.model.menu.Menu;
import com.qi.xiaohui.dingdang.model.table.Table;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by TQi on 4/6/16.
 */
public interface Gateway {

    @GET("menu/{language}")
    Call<List<Menu>> getMenu(@Path("language") String language);

    @GET("news/{tablename}")
    Call<Table> getTable(@Path("tablename") String tablename, @Header("pagination") String pagination,@Header("language") String language);

    @GET("content")
    Call<List<WebContent>> getWenContent(@Header("link") String link,@Header("id") String id);
}
