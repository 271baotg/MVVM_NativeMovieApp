package com.example.nativemovieapp.Fragments;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.FavoriteMovieAdapter;
import com.example.nativemovieapp.adapter.HomeSliderAdapter;
import com.example.nativemovieapp.adapter.RcvInterfce;
import com.example.nativemovieapp.viewmodel.AuthenticationViewModel;
import com.example.nativemovieapp.viewmodel.FavoriteViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class Favorite extends Fragment implements RcvInterfce {

    public FavoriteViewModel favVm;
    private RecyclerView recyclerView;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObserveChange(this);
        favVm.loadListFavor();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favVm = new ViewModelProvider(this).get(FavoriteViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = root.findViewById(R.id.favor_rcv);
        layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        return root;
    }

    public void ObserveChange(RcvInterfce rcvInterfce){
        favVm.getFavorList().observe(getViewLifecycleOwner(), new Observer<List<MovieDetail>>() {
            @Override
            public void onChanged(List<MovieDetail> movieDetails) {
                Log.d("FavorLiveData", "Change");
                favoriteMovieAdapter = new FavoriteMovieAdapter(movieDetails, rcvInterfce);
                recyclerView.setAdapter(favoriteMovieAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.d("status", "onResume: ");
    }

    @Override
    public void onMovieClick(Movie movie, int id) {

    }

    @Override
    public void onMovieFavorClick(MovieDetail movieDetail) {
        int id = movieDetail.getId();
        NavDirections action = FavoriteDirections.actionFavoriteToMovieDetailFragment(id);
        Navigation.findNavController(getActivity(), R.id.host_fragment).navigate(action);
    }
}