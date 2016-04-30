package com.qi.xiaohui.dingdang.application;

import android.app.Application;

import com.qi.xiaohui.dingdang.dao.DataStore;

/**
 * Created by TQi on 4/29/16.
 */
public class DingDangApplication extends Application {
    private static DataStore dataStore;
    @Override
    public void onCreate() {
        super.onCreate();
        dataStore = new DataStore();
    }

    public static DataStore getDataStore(){
        return dataStore;
    }
}
