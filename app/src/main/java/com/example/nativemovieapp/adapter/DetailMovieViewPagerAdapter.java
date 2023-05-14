package com.example.nativemovieapp.adapter;

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
        switch (position){
            case 0:
                return new MovieDetailFragmentTrailers();
            case 1:
                return new MovieDetailFragmentImages();
            case 2:
                return new MovieDetailFragmentSimilar(mParentFragment,midCurrent);
            default:
                return new MovieDetailFragmentTrailers();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}