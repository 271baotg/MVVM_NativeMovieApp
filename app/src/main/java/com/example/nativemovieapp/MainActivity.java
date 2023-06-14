package com.example.nativemovieapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.nativemovieapp.Api.Credential;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import org.jetbrains.annotations.NotNull;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import static com.example.nativemovieapp.utils.Greet.showWelcome;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {


    private BottomSheetDialog dialog;
    private BottomNavigationView navigation;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private View layout;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Ẩn thanh trạng thái
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        layout = findViewById(R.id.host_fragment);
        layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //Khởi tạo và gắn Navigation bar
        navigation = findViewById(R.id.nav_bar);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.host_fragment);
        navController = Navigation.findNavController(this, R.id.host_fragment);
        showWelcome(Credential.getCurrentUser().getDisplayName().toString(), "Have a great date", this);
        NavigationUI.setupWithNavController(navigation, navController);


        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                NavigationUI.onNavDestinationSelected(item, navHostFragment.getNavController());
                navHostFragment.getNavController().popBackStack(item.getItemId(), false);

                return true;
            }
        });


    }


}