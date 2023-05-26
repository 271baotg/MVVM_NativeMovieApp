package com.example.nativemovieapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Model.MovieTrailer;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.SearchAdapter;
import com.example.nativemovieapp.adapter.TrailersAdapter;
import com.example.nativemovieapp.viewmodel.MovieDetailViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MovieDetailFragmentTrailers extends Fragment {
    Fragment mParentFragment;
    int midCurrent;
    private MovieDetailViewModel detailVM;
    private RecyclerView rcvTrailers;
    private TrailersAdapter trailersAdapter;

    public MovieDetailFragmentTrailers(Fragment parentFragment, int idCurrent) {
        mParentFragment = parentFragment;
        midCurrent = idCurrent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gắn ViewModel
        detailVM = new ViewModelProvider(this).get(MovieDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trailers, container, false);

        rcvTrailers = root.findViewById(R.id.rcv_trailers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvTrailers.setLayoutManager(linearLayoutManager);

        ObserveChange();
        return root;
    }

    public void ObserveChange() {
        detailVM.getMovieTrailers().observe(getViewLifecycleOwner(), new Observer<List<MovieTrailer>>() {
            @Override
            public void onChanged(List<MovieTrailer> listTrailer) {
                trailersAdapter = new TrailersAdapter(listTrailer, mParentFragment.getContext());
                rcvTrailers.setAdapter(trailersAdapter);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (trailersAdapter != null) {
            trailersAdapter.Release();
        }
    }
}
