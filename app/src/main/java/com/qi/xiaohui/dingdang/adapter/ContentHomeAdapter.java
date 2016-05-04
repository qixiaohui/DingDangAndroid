package com.qi.xiaohui.dingdang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qi.xiaohui.dingdang.application.DingDangApplication;
import com.qi.xiaohui.dingdang.dao.DataStore;
import com.qi.xiaohui.dingdang.fragment.ContentHomeFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by TQi on 4/30/16.
 */
public class ContentHomeAdapter extends FragmentStatePagerAdapter {
    private DataStore dataStore;
    private List<String> mTitles;
    private HashMap<Integer, Fragment> fragmentCache;

    public ContentHomeAdapter(FragmentManager fragmentManager, List<String> titles){
        super(fragmentManager);
        if(dataStore == null){
            dataStore = DingDangApplication.getDataStore();
        }
        if(fragmentCache == null){
            fragmentCache = new HashMap<>();
        }
        mTitles = titles;
    }

    public Fragment getCachedFragment(int position){
        if(fragmentCache.get(position) != null){
            return fragmentCache.get(position);
        }else{
            return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ContentHomeFragment.newInstance(mTitles.get(position));
        if(fragmentCache.get(position) == null){
            fragmentCache.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
