package com.example.nativemovieapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.LiveDataProvider;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Movies;
import com.example.nativemovieapp.adapter.HomeSliderAdapter;
import com.example.nativemovieapp.adapter.SearchAdapter;
import com.example.nativemovieapp.adapter.TopRateAdapter;
import com.example.nativemovieapp.adapter.UpcomingAdapter;
import com.example.nativemovieapp.viewmodel.SearchViewModels;

import java.util.ArrayList;
import java.util.List;

import static com.example.nativemovieapp.Api.LiveDataProvider.movieListFinal;


public class Search extends Fragment {
    //khoi tao viewModel
    private SearchViewModels searchVM = new SearchViewModels();
    private SearchAdapter searchAdapter ;
    private UpcomingAdapter upcomingAdapter ;
    private TopRateAdapter topRateAdapter;
    private RecyclerView rcvSearch;
    private RecyclerView rcvUpcoming;

    private RecyclerView rcvRateTop;
    private SearchView searchView;
    private TextView upcomingTitle;
    private TextView toprateTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gắn ViewModel
        searchVM = new ViewModelProvider(this).get(SearchViewModels.class);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        rcvSearch = root.findViewById(R.id.rcv_movie);
        rcvUpcoming=root.findViewById(R.id.rcv_upcoming);
        rcvRateTop = root.findViewById(R.id.rcv_toprate);
        searchView = root.findViewById(R.id.sv_search);
        upcomingTitle = root.findViewById(R.id.upcoming_title);
        toprateTitle = root.findViewById(R.id.ratetop_title);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty())
                {
                    rcvUpcoming.setVisibility(View.VISIBLE);
                    upcomingTitle.setVisibility(View.VISIBLE);
                    rcvRateTop.setVisibility(View.VISIBLE);
                    toprateTitle.setVisibility(View.VISIBLE);
                }
                else {
                    rcvUpcoming.setVisibility(View.GONE);
                    upcomingTitle.setVisibility(View.GONE);
                    rcvRateTop.setVisibility(View.GONE);
                    toprateTitle.setVisibility(View.GONE);
                }
                movieListFinal= new ArrayList<>();
                searchVM.loadListSearchMovie(query, root.getContext());
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                {
                    rcvUpcoming.setVisibility(View.VISIBLE);
                    upcomingTitle.setVisibility(View.VISIBLE);
                    rcvRateTop.setVisibility(View.VISIBLE);
                    toprateTitle.setVisibility(View.VISIBLE);
                }
                else
                {
                    rcvUpcoming.setVisibility(View.GONE);
                    upcomingTitle.setVisibility(View.GONE);
                    rcvRateTop.setVisibility(View.GONE);
                    toprateTitle.setVisibility(View.GONE);
                }
                movieListFinal= new ArrayList<>();
                searchVM.loadListSearchMovie(newText, root.getContext());
                return false;
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false);
        rcvSearch.setLayoutManager(linearLayoutManager);
        rcvUpcoming.setLayoutManager(linearLayoutManager2);
        rcvRateTop.setLayoutManager(linearLayoutManager3);

        searchVM.loadListSearchMovie("", root.getContext());
        Context context= getParentFragment().getContext();
        Context context1=getParentFragment().getContext();
        searchVM.loadListUpcomingMovie();
        searchVM.loadListTopRateMovie();
        ObserveChange();
        ObserveChangeUpcoming();
        ObserveChangeTopRate();
        return root;
    }
    public void ObserveChange() {
        searchVM.getListSearch().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                searchAdapter = new SearchAdapter(getParentFragment().getContext(),movies);
                rcvSearch.setAdapter(searchAdapter);
            }
        });
    }
    public void ObserveChangeUpcoming() {
        searchVM.getListUpcoming().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                upcomingAdapter = new UpcomingAdapter(getParentFragment().getContext(),movies);
                rcvUpcoming.setAdapter(upcomingAdapter);
            }
        });
    }
    public void ObserveChangeTopRate() {
        searchVM.getListTopRate().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                topRateAdapter = new TopRateAdapter(getParentFragment().getContext(),movies);
                rcvRateTop.setAdapter(topRateAdapter);
            }
        });
    }
}