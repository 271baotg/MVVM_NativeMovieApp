package com.example.nativemovieapp.adapter;

import android.app.FragmentTransaction;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.nativemovieapp.MovieDetailFragmentImages;
import com.example.nativemovieapp.MovieDetailFragmentSimilar;
import com.example.nativemovieapp.MovieDetailFragmentTrailers;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import org.jetbrains.annotations.NotNull;

public class DetailMovieViewPagerAdapter extends FragmentStateAdapter {

    private final Fragment mParentFragment;
    private int midCurrent;
    public DetailMovieViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, Fragment parentFragment, Integer idCurrent) {
        super(fragmentActivity);
        mParentFragment=parentFragment;
        midCurrent=idCurrent;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {

        Log.d("positionfra", String.valueOf(position));
        switch (position){
            case 0:
                return new MovieDetailFragmentTrailers(mParentFragment,midCurrent);
            case 1:
                return new MovieDetailFragmentImages();
            case 2:
                return new MovieDetailFragmentSimilar(mParentFragment,midCurrent);
            default:
                return new MovieDetailFragmentTrailers(mParentFragment, midCurrent);
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }



}
