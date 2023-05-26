package com.example.nativemovieapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.View;
import android.webkit.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.nativemovieapp.R;
import com.example.nativemovieapp.viewmodel.MovieDetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;

import java.util.HashMap;
import java.util.Map;


public class PlayerViewMovieFragment extends AppCompatActivity {
    private String path;
    private MovieDetailViewModel detailVM;
    private Context context;

    private Integer id;
    WebView webview;

    View layout;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_player_view_movie);
        detailVM = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id= bundle.getInt("id");
        }

//        path = "https://www.2embed.to/embed/tmdb/movie?id="+id;
        //https://2embed.org/embed/movie?tmdb=787459
//        path = "https://2embed.org/embed/movie?tmdb="+id;
        path = "https://v2.vidsrc.me/embed/"+ id;
        Log.d("testId", path);

        webview = findViewById(R.id.webview_play);
        layout = findViewById(R.id.layout_playerview);
        layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        new AdBlockerWebView.init(this).initializeWebView(webview);
        webview.setWebViewClient(new Browser_home());
        webview.loadUrl(path);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }else{
            super.onBackPressed();
        }
    }

    private class Browser_home extends WebViewClient {

        Browser_home() {}

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(path.startsWith("https://v2.vidsrc.me/embed/"))
            {
                return true;
            }
            else{
                webview.goBack();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }





}