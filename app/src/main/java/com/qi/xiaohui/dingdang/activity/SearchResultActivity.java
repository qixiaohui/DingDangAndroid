package com.qi.xiaohui.dingdang.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.adapter.NewsAdapter;
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
 * Created by TQi on 5/3/16.
 */
public class SearchResultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecycleView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;
    private int previousPage = 0;
    private int maxSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        mRecycleView = (RecyclerView) findViewById(R.id.resultView);
        _handleIntent(getIntent());
    }

    private void _buildRecycleView(final String query){
        mRecycleView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(SearchResultActivity.this);
        mRecycleView.setLayoutManager(mLayoutManager);
        getResults(query, "1");
        mRecycleView.addOnScrollListener(new EndlessRecyclerListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page != previousPage) {
                    previousPage = current_page;
                } else {
                    return;
                }
                if (maxSize > mNewsAdapter.getItemCount()) {
                    getResults(query, Integer.toString((current_page - 1) * 10 + 1));
                }
            }
        });
    }

    private void getResults(final String query, final String pageIndex){
        Gateway gateway = RestClient.getGateway();
        Call<Table> tableCall = gateway.getTable(query, pageIndex, Locale.getDefault().getLanguage());
        tableCall.enqueue(new Callback<Table>() {
            @Override
            public void onResponse(Call<Table> call, Response<Table> response) {
                if (response.body() != null) {
                    if (Integer.parseInt(pageIndex) == 1) {
                        maxSize = response.body().getCount();
                        _setAdapter((ArrayList) response.body().getResults(), query);
                    } else {
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        _handleIntent(intent);
    }

    private void _handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            toolbar.setTitle(query);
            setSupportActionBar(toolbar);
            _buildRecycleView(query);
        }
    }

    private void _setAdapter(ArrayList<Result> results, String title){
        mNewsAdapter = new NewsAdapter(results, getApplicationContext(), SearchResultActivity.this, title);
        mRecycleView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
