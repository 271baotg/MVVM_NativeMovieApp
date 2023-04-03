package com.example.nativemovieapp;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.LiveDataProvider;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Movies;
import com.example.nativemovieapp.adapter.HomeSliderAdapter;
import com.example.nativemovieapp.adapter.SearchAdapter;
import com.example.nativemovieapp.viewmodel.SearchViewModels;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {

    //khoi tao viewModel
    private SearchViewModels searchVM = new SearchViewModels();
    private SearchAdapter searchAdapter ;

    private RecyclerView rcvSearch;
    private TextView tvMinimun;
    private SearchView searchView;

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
        searchView = root.findViewById(R.id.sv_search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchVM.loadListSearchMovie(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchVM.loadListSearchMovie(newText);

                return false;
            }

        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(root.getContext(), 2);
        rcvSearch.setLayoutManager(gridLayoutManager);

//        //Khởi tạo adapter với danh sách phim trống và đặt adapter cho RecyclerView
//        searchAdapter = new SearchAdapter(getParentFragment().getContext(), new ArrayList<Movie>(), new ArrayList<Movie>());
//        rcvSearch.setAdapter(searchAdapter);
        searchVM.loadListSearchMovie("");
        ObserveChange();
        return root;
    }

    public void ObserveChange() {
        searchVM.getListSearch().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                searchAdapter = new SearchAdapter(getParentFragment().getContext(),movies,movies);
                rcvSearch.setAdapter(searchAdapter);
            }
        });
    }
//    private void filterList(String newText) {
//        List<Movie> filteredList = new ArrayList<>();
//        for(Movie item: LiveDataProvider.getListSearch().getValue())
//        {
//            if(item.getTitle().toLowerCase().contains(newText.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//
//        if(filteredList.isEmpty()){
//            Toast.makeText(getContext(),"no data found",Toast.LENGTH_SHORT).show();
//            searchAdapter = new SearchAdapter(getParentFragment().getContext(), null);
//            rcvSearch.setAdapter(searchAdapter);
//        }
//        else{
//            searchAdapter.setFilteredList(filteredList);
//        }
//    }
}