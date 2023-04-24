package com.example.nativemovieapp;

import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import org.jetbrains.annotations.NotNull;

import static androidx.navigation.fragment.FragmentKt.findNavController;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView navigation;
    private NavHostFragment navHostFragment;

    private NavController navController;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Ẩn thanh trạng thái
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Khởi tạo và gắn Navigation bar
        navigation = findViewById(R.id.nav_bar);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.host_fragment);
        navController = Navigation.findNavController(this, R.id.host_fragment);
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