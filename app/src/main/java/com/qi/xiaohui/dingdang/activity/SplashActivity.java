package com.qi.xiaohui.dingdang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.application.DingDangApplication;
import com.qi.xiaohui.dingdang.dao.DataStore;
import com.qi.xiaohui.dingdang.dao.Gateway;
import com.qi.xiaohui.dingdang.dao.RestClient;
import com.qi.xiaohui.dingdang.model.menu.Menu;
import com.qi.xiaohui.dingdang.model.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TQi on 4/29/16.
 */
public class SplashActivity extends Activity {
    private DataStore dataStore;
    private ImageView splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (ImageView) findViewById(R.id.splashIcon);
        splash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        _requestData();
    }

    private void _requestData(){
        String language = Locale.getDefault().getLanguage();
        Gateway gateway = RestClient.getGateway();
        dataStore = DingDangApplication.getDataStore();
        if(dataStore.getMenus().size() > 0 && dataStore.getResults(getResources().getString(R.string.default_category)) != null){
            _goToMain();
        }else{
            Call<List<Menu>> menusCall = gateway.getMenu(language);
            menusCall.enqueue(new Callback<List<Menu>>() {
                @Override
                public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                    dataStore.setMenus((ArrayList)response.body());
                    if(dataStore.getResults(getResources().getString(R.string.default_category)) != null ){
                        _goToMain();
                    }
                }

                @Override
                public void onFailure(Call<List<Menu>> call, Throwable t) {

                }
            });

            //default trending
            Call<Table> tableCall = gateway.getTable(getResources().getString(R.string.default_category), "1", language);
            tableCall.enqueue(new Callback<Table>() {
                @Override
                public void onResponse(Call<Table> call, Response<Table> response) {
                    if(response.body() != null) {
                        dataStore.setResults(getResources().getString(R.string.default_category), (ArrayList) response.body().getResults());
                        dataStore.setMax(getResources().getString(R.string.default_category), response.body().getCount());
                        if (dataStore.getMenus().size() > 0) {
                            _goToMain();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Table> call, Throwable t) {
                    Log.i("error", t.toString());
                }
            });
        }
    }

    private void _goToMain(){
        HomeActivity.launchActivity(SplashActivity.this);
        finish();
    }
}
