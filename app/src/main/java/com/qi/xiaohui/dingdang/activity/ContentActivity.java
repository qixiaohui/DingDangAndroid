package com.qi.xiaohui.dingdang.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.dao.Gateway;
import com.qi.xiaohui.dingdang.dao.RestClient;
import com.qi.xiaohui.dingdang.model.table.Result;
import com.qi.xiaohui.dingdang.model.webcontent.WebContent;

import java.net.SocketTimeoutException;
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
    private Button visitWebsite;
    private boolean firstTimeLoad = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        webView = (WebView)findViewById(R.id.webView);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        visitWebsite = (Button) findViewById(R.id.visitWebsite);
        result = new Gson().fromJson(getIntent().getStringExtra(CONTENT_EXTRA), Result.class);
        title.setText(result.getTitle());
        date.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date((long) (result.getDate() * 1000))));
        _requestWebContent(result.getUrl(), result.getTitle().replaceAll("[^a-zA-Z0-9]+", ""));
    }

    private void _requestWebContent(String url, String id){
        Gateway gateway = RestClient.getGateway();
        Log.i("url", url);
        Call<List<WebContent>> webContent = gateway.getWebContent(url, id);
        webContent.enqueue(new Callback<List<WebContent>>() {
            @Override
            public void onResponse(Call<List<WebContent>> call, Response<List<WebContent>> response) {
                _loadPage(response.body().get(0).getContent());
            }

            @Override
            public void onFailure(Call<List<WebContent>> call, Throwable t) {
                Log.i("error", t.toString());
                _loadUrl();
            }
        });
    }

    private void _loadPage(String dom){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float width  = outMetrics.widthPixels / density - 30;
        dom = dom.replaceAll("<img", "<img width='"+Float.toString(width)+"'");
        webView.loadData(dom, "text/html; charset=utf-8", "UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (firstTimeLoad) {
                            if (webView.getMeasuredHeight() > 900) {
                                visitWebsite.setVisibility(View.VISIBLE);
                            } else {
                                _loadUrl();
                            }
                            firstTimeLoad = !firstTimeLoad;
                        }
                    }
                }, 1000);
            }


        });
    }

    private void _loadUrl() {
        title.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        webView.loadUrl(result.getUrl());
    }

    public static void launchActivity(Activity fromActivity, Result result){
        Intent intent = new Intent(fromActivity, ContentActivity.class);
        intent.putExtra(CONTENT_EXTRA, new Gson().toJson(result));
        fromActivity.startActivity(intent);
    }
}
