package com.hanabi.apircvdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.EXTRA_URL_NEWS);

        webView = findViewById(R.id.wv_detail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }


}