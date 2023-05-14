package com.example.nativemovieapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.example.nativemovieapp.viewmodel.HomeViewModels;
import com.example.nativemovieapp.viewmodel.SearchViewModels;

public class SplashActivity extends AppCompatActivity {

    TextView tvTitle;
    RelativeLayout relativeLayout;
    Animation tvAnimation, layoutAnimation;
    SearchViewModels searchViewModels = new SearchViewModels();
    HomeViewModels homeViewModels = new HomeViewModels();

    View splashActivity;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        searchViewModels = new ViewModelProvider(this).get(SearchViewModels.class);
        homeViewModels = new ViewModelProvider(this).get(HomeViewModels.class);

        tvTitle = findViewById(R.id.tvTitle);
        relativeLayout = findViewById(R.id.splashActivity);
        splashActivity = findViewById(R.id.lottie_splash);
        splashActivity.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        tvAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_to_top);
        loadAPI();
        loadData();

    }

    private void loadData() {
        Log.d("CheckInternet", String.valueOf(AppUtil.isNetworkAvailable(this)));
        if(AppUtil.isNetworkAvailable(this)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.setAnimation(layoutAnimation);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.setAnimation(tvAnimation);
                        }
                    },1500);
                }
            },500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,AuthenticationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();
                }
            },4000);
        }
        else{
            Toast.makeText(this,"Network disconnected",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAPI(){
        searchViewModels.loadListUpcomingMovie();
        searchViewModels.loadListTopRateMovie();
        homeViewModels.loadListPopularMovie();
        homeViewModels.loadListCategory();
        homeViewModels.loadListHomeTopRate();
        homeViewModels.loadListUpComing();
    }
}