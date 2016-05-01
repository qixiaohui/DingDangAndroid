package com.qi.xiaohui.dingdang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.dao.Gateway;
import com.qi.xiaohui.dingdang.dao.RestClient;
import com.qi.xiaohui.dingdang.model.webcontent.WebContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TQi on 4/29/16.
 */
public class ContentActivity extends AppCompatActivity{
    private WebView webView;
    private String url;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        webView = (WebView)findViewById(R.id.webView);
        url = getIntent().getStringExtra(HomeActivity.URL);
        id = getIntent().getStringExtra(HomeActivity.ID);
        _requestWebContent(url, id);
    }

    private void _requestWebContent(String url, String id){
        Gateway gateway = RestClient.getGateway();
        Call<List<WebContent>> webContent = gateway.getWenContent(url, id);
        webContent.enqueue(new Callback<List<WebContent>>() {
            @Override
            public void onResponse(Call<List<WebContent>> call, Response<List<WebContent>> response) {
                _loadPage(response.body().get(0).getContent());
            }

            @Override
            public void onFailure(Call<List<WebContent>> call, Throwable t) {
                Log.i("error", t.toString());
            }
        });
    }

    private void _loadPage(String dom){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        Log.i("web", dom);
        webView.loadData(dom, "text/html", "UTF-8");
    }

    public static void launchActivity(Activity fromActivity, String url, String id){
        Intent intent = new Intent(fromActivity, ContentActivity.class);
        intent.putExtra(HomeActivity.URL, url);
        intent.putExtra(HomeActivity.ID, id);
        fromActivity.startActivity(intent);
    }
}
