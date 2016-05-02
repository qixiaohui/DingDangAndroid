package com.qi.xiaohui.dingdang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.dao.Gateway;
import com.qi.xiaohui.dingdang.dao.RestClient;
import com.qi.xiaohui.dingdang.model.table.Result;
import com.qi.xiaohui.dingdang.model.webcontent.WebContent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TQi on 4/29/16.
 */
public class ContentActivity extends AppCompatActivity{
    public static final String CONTENT_EXTRA = "CONTENT_EXTRA";

    private WebView webView;
    private TextView title;
    private TextView date;
    private Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        webView = (WebView)findViewById(R.id.webView);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        result = new Gson().fromJson(getIntent().getStringExtra(CONTENT_EXTRA), Result.class);
        title.setText(result.getTitle());
        date.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date((long)(result.getDate()*1000))));
        _requestWebContent(result.getUrl(), result.getTitle().replaceAll("[^a-zA-Z0-9]+", ""));
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
        webView.loadData(dom, "text/html; charset=utf-8", "UTF-8");
    }

    public static void launchActivity(Activity fromActivity, Result result){
        Intent intent = new Intent(fromActivity, ContentActivity.class);
        intent.putExtra(CONTENT_EXTRA, new Gson().toJson(result));
        fromActivity.startActivity(intent);
    }
}
