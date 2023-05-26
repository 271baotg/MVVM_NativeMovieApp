package com.example.nativemovieapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nativemovieapp.Fragments.MovieDetailFragmentDirections;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.RcvInterfce;
import com.example.nativemovieapp.adapter.SimilarMovieAdapter;
import com.example.nativemovieapp.viewmodel.MovieDetailViewModel;

import java.util.List;

public class MovieDetailFragmentSimilar extends Fragment implements RcvInterfce {

    private Fragment mParentFragment;
    private int midCurrent;

    public MovieDetailFragmentSimilar(Fragment parentFragment, int idCurrent) {
        mParentFragment = parentFragment;
        midCurrent = idCurrent;
    }

    private MovieDetailViewModel detailVM;
    public RecyclerView rcvSimilarMovie;
    private SimilarMovieAdapter similarMovieAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gắn ViewModel
        detailVM = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        Log.d("similarId", String.valueOf(midCurrent));
        detailVM.loadListSimilarMovie(midCurrent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_similar, container, false);
        rcvSimilarMovie = root.findViewById(R.id.rcv_similarMovie);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvSimilarMovie.setNestedScrollingEnabled(false);
        rcvSimilarMovie.setLayoutManager(gridLayoutManager);
        ObserveChangeSimilarMovie(this);
        return root;
    }

    public void ObserveChangeSimilarMovie(RcvInterfce rcvInterfce) {
        detailVM.getsimilarMovie().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (mParentFragment != null) {
                    Log.d("movieDetail", movies.toString());
                    similarMovieAdapter = new SimilarMovieAdapter(mParentFragment.getContext(), movies, rcvInterfce);
                    rcvSimilarMovie.setAdapter(similarMovieAdapter);
                } else {
                    Log.d("contextnull", "null");
                }
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie, int idMovie) {
        int id = movie.getId();
        NavDirections action = MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieDetailFragment(id);
        Navigation.findNavController(getActivity(), R.id.host_fragment).navigate(action);
    }

    @Override
    public void onMovieFavorClick(MovieDetail movieDetail) {

    }


}
