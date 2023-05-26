package com.example.nativemovieapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nativemovieapp.Fragments.MovieDetailFragmentComments;
import com.example.nativemovieapp.Fragments.MovieDetailFragmentSimilar;
import com.example.nativemovieapp.Fragments.MovieDetailFragmentTrailers;
import org.jetbrains.annotations.NotNull;

public class DetailMovieViewPagerAdapter extends FragmentStateAdapter {

    private final Fragment mParentFragment;
    private int midCurrent;

    public DetailMovieViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, Fragment parentFragment, Integer idCurrent) {
        super(fragmentActivity);
        mParentFragment = parentFragment;
        midCurrent = idCurrent;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MovieDetailFragmentTrailers(mParentFragment, midCurrent);
            case 1:
                return new MovieDetailFragmentComments(mParentFragment, midCurrent);
            case 2:
                return new MovieDetailFragmentSimilar(mParentFragment, midCurrent);
            default:
                return new MovieDetailFragmentTrailers(mParentFragment, midCurrent);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
