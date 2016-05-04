package com.qi.xiaohui.dingdang.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.dao.Gateway;
import com.qi.xiaohui.dingdang.dao.RestClient;
import com.qi.xiaohui.dingdang.model.table.Result;
import com.qi.xiaohui.dingdang.model.webcontent.WebContent;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TQi on 4/29/16.
 */
public class ContentActivity extends AppCompatActivity{
    public static final String CONTENT_EXTRA = "CONTENT_EXTRA";
    public static final String SUBTITLE = "SUBTITLE";

    private WebView webView;
    private TextView title;
    private TextView date;
    private Result result;
    private Button visitWebsite;
    private Toolbar toolbar;
    private Button button;
    private ScrollView scrollView;
    private WebView mWebContent;
    private ProgressBar progressBar;

    private boolean firstTimeLoad = true;
    private String subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        webView = (WebView)findViewById(R.id.webView);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        visitWebsite = (Button) findViewById(R.id.visitWebsite);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        button = (Button) findViewById(R.id.visitWebsite);
        scrollView = (ScrollView) findViewById(R.id.scraperView);
        mWebContent = (WebView) findViewById(R.id.webContent);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        subtitle = getIntent().getStringExtra(SUBTITLE);
        toolbar.setTitle(subtitle);
        setSupportActionBar(toolbar);

        result = new Gson().fromJson(getIntent().getStringExtra(CONTENT_EXTRA), Result.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = result.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        title.setText(result.getTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        date.setText(simpleDateFormat.format(new Date(Long.parseLong(result.getDate()))));
        _requestWebContent(result.getUrl(), result.getTitle().replaceAll("[^a-zA-Z0-9]+", ""));
    }

    private void _requestWebContent(String url, String id){
        Gateway gateway = RestClient.getGateway();
        final Call<List<WebContent>> webContent = gateway.getWebContent(url, id);
        webContent.enqueue(new Callback<List<WebContent>>() {
            @Override
            public void onResponse(Call<List<WebContent>> call, Response<List<WebContent>> response) {
                if (response.body() != null) {
                    _loadPage(response.body().get(0).getContent());
                }
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
        //dom = dom.replaceAll("<img", "<img width='"+Float.toString(width)+"'");
        dom= "<script type='text/javascript'>document.addEventListener('DOMContentLoaded', function(event){" +
                "var image = document.getElementsByTagName('img');"+
                "Array.prototype.forEach.call(image, function(element){element.removeAttribute('style');" +
                "element.removeAttribute('width');"+
                "element.removeAttribute('height');"+
                "element.style.marginRight='30px';"+
                "element.width = "+Float.toString(width)+"});"+
                "});"+
                "</script>"+dom;
        webView.loadData(dom, "text/html; charset=utf-8", "UTF-8");
        visitWebsite.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (firstTimeLoad) {
                            if (webView.getMeasuredHeight() < 500) {
                                scrollView.setVisibility(View.GONE);
                                _loadUrl();

                            }else{
                                progressBar.setVisibility(View.GONE);
                            }
                            firstTimeLoad = !firstTimeLoad;
                        }else{
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, 500);
            }


        });
    }

    private void _loadUrl() {
        title.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        visitWebsite.setVisibility(View.GONE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        mWebContent.setVisibility(View.VISIBLE);
        mWebContent.setVerticalScrollBarEnabled(true);
        mWebContent.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //prevent launch from external browser
        mWebContent.setWebViewClient(new WebViewClient());
        mWebContent.loadUrl(result.getUrl());
    }

    public static void launchActivity(Activity fromActivity, Result result, String subtitle){
        Intent intent = new Intent(fromActivity, ContentActivity.class);
        intent.putExtra(CONTENT_EXTRA, new Gson().toJson(result));
        intent.putExtra(SUBTITLE, subtitle);
        fromActivity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_content_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, result.getTitle());
            i.putExtra(Intent.EXTRA_TEXT, result.getUrl());
            startActivity(Intent.createChooser(i, result.getTitle()));
            return true;
        }else if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
