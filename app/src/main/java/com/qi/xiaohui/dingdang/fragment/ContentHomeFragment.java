package com.qi.xiaohui.dingdang.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.adapter.NewsAdapter;
import com.qi.xiaohui.dingdang.application.DingDangApplication;
import com.qi.xiaohui.dingdang.dao.DataStore;
import com.qi.xiaohui.dingdang.dao.Gateway;
import com.qi.xiaohui.dingdang.dao.RestClient;
import com.qi.xiaohui.dingdang.listener.EndlessRecyclerListener;
import com.qi.xiaohui.dingdang.model.table.Result;
import com.qi.xiaohui.dingdang.model.table.Table;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TQi on 4/30/16.
 */
public class ContentHomeFragment extends android.support.v4.app.Fragment {
    public static final String PAGE_TITLE = "PAGE_TITLE";
    private DataStore dataStore;
    private String pageTitle = "";
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private NewsAdapter mNewsAdapter;
    private String title;
    private int previousPage = 0;
    //max items can get from server
    private int maxSize = 0;

    public static ContentHomeFragment newInstance(String title){
        Bundle arg = new Bundle();
        arg.putString(PAGE_TITLE, title);
        ContentHomeFragment contentHomeFragment = new ContentHomeFragment();
        contentHomeFragment.setArguments(arg);
        return contentHomeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTitle = getArguments().getString(PAGE_TITLE);
        if(dataStore == null){
            dataStore = DingDangApplication.getDataStore();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_home, container, false);
        mRecycleView = (RecyclerView) view.findViewById(R.id.resultView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecycleView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(mLayoutManager);
        _buildScrollListener();
        if(dataStore.getResults(pageTitle) != null) {
            _setAdapter(dataStore.getResults(pageTitle), pageTitle);
        }else{
            getResults(pageTitle, "1");
        }
        return view;
    }

    private void getResults(final String title, final String pageIndex){
        Gateway gateway = RestClient.getGateway();
        Call<Table> tableCall = gateway.getTable(title, pageIndex, Locale.getDefault().getLanguage());
        tableCall.enqueue(new Callback<Table>() {
            @Override
            public void onResponse(Call<Table> call, Response<Table> response) {
                if(response.body() != null) {
                    if (Integer.parseInt(pageIndex) == 1) {
                        dataStore.setResults(title, (ArrayList) response.body().getResults());
                        dataStore.setMax(title, response.body().getCount());
                        _setAdapter((ArrayList) response.body().getResults(), title);
                    } else {
                        dataStore.appendTable(title, (ArrayList) response.body().getResults(), Integer.parseInt(pageIndex));
                        mNewsAdapter.addResults((ArrayList) response.body().getResults(), Integer.parseInt(pageIndex));
                        mNewsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Table> call, Throwable t) {
                Log.i("error", t.toString());
            }
        });
    }

    private void _setAdapter(ArrayList<Result> results, String title){
        progressBar.setVisibility(View.GONE);
        mNewsAdapter = new NewsAdapter(results, getContext(), getActivity(), title);
        mRecycleView.setAdapter(mNewsAdapter);
        maxSize = dataStore.getMax(title);
        mNewsAdapter.notifyDataSetChanged();
    }

    public void refreshView(){
        previousPage = 0;
        maxSize = 0;
        progressBar.setVisibility(View.VISIBLE);
        getResults(pageTitle, "1");
        _buildScrollListener();
    }

    private void _buildScrollListener(){
        mRecycleView.addOnScrollListener(new EndlessRecyclerListener((LinearLayoutManager)mLayoutManager){
            @Override
            public void onLoadMore(int current_page) {
                if(current_page != previousPage){
                    previousPage = current_page;
                }else{
                    return;
                }
                if(maxSize > dataStore.getResults(pageTitle).size()){
                    getResults(pageTitle, Integer.toString((current_page-1)*10+1));
                }
            }
        });
    }
}


