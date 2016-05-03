package com.qi.xiaohui.dingdang.dao;

import android.content.Context;
import android.util.Log;
import android.util.LruCache;

import com.qi.xiaohui.dingdang.model.menu.Menu;
import com.qi.xiaohui.dingdang.model.table.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TQi on 4/29/16.
 */
public class DataStore {
    private static HashMap<String, Integer> maxSize;
    private static LruCache<String, ArrayList<Result>> mTableStore;
    private static List<Menu> mMenus;

    public DataStore() {
        if(mTableStore == null){
            mTableStore = new LruCache<>(20);

        }

        if(mMenus == null) {
            mMenus = new ArrayList<>();
        }

        if(maxSize == null){
            maxSize = new HashMap<>();
        }
    }

    public void setMax(String key, int max){
        maxSize.put(key, max);
    }

    public int getMax(String key){
        return maxSize.get(key);
    }

    public void setResults(String key, ArrayList<Result> results){
        mTableStore.remove(key);
        mTableStore.put(key, results);
    }

    public ArrayList<Result> getResults(String key){
        return mTableStore.get(key);
    }

    public void appendTable(String key, ArrayList<Result> results, int pageSize){
        if (pageSize>=mTableStore.get(key).size()) {
            mTableStore.get(key).addAll(results);
        }
    }

    public void setMenus(ArrayList<Menu> menus){
        mMenus = menus;
    }

    public List<Menu> getMenus(){
        return mMenus;
    }
}
