package com.qi.xiaohui.dingdang.dao;

import android.content.Context;

import com.qi.xiaohui.dingdang.model.menu.Menu;
import com.qi.xiaohui.dingdang.model.table.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TQi on 4/29/16.
 */
public class DataStore {
    private static HashMap<String, ArrayList<Result>> mTableStore;
    private static List<Menu> mMenus;

    public DataStore() {
        if(mTableStore == null){
            mTableStore = new HashMap<>();
        }

        if(mMenus == null) {
            mMenus = new ArrayList<>();
        }
    }

    public void setResults(String key, ArrayList<Result> results){
        mTableStore.remove(key);
        mTableStore.put(key, results);
    }

    public ArrayList<Result> getResults(String key){
        return mTableStore.get(key);
    }

    public void appendTable(String key, ArrayList<Result> results){
        mTableStore.get(key).addAll(results);
    }

    public void setMenus(ArrayList<Menu> menus){
        mMenus = menus;
    }

    public List<Menu> getMenus(){
        return mMenus;
    }
}
