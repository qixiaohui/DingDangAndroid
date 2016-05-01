package com.qi.xiaohui.dingdang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qi.xiaohui.dingdang.application.DingDangApplication;
import com.qi.xiaohui.dingdang.dao.DataStore;
import com.qi.xiaohui.dingdang.fragment.ContentHomeFragment;

import java.util.List;

/**
 * Created by TQi on 4/30/16.
 */
public class ContentHomeAdapter extends FragmentStatePagerAdapter {
    private DataStore dataStore;
    private List<String> mTitles;

    public ContentHomeAdapter(FragmentManager fragmentManager, List<String> titles){
        super(fragmentManager);
        if(dataStore == null){
            dataStore = DingDangApplication.getDataStore();
        }
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return ContentHomeFragment.newInstance(mTitles.get(position));
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
