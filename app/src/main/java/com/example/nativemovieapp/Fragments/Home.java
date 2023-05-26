package com.example.nativemovieapp.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;

import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.*;
import com.example.nativemovieapp.viewmodel.HomeViewModels;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Home extends Fragment implements RcvInterfce {

    private SliderView sliderView;

    private RecyclerView categoryRecycler;
    private HomeCategoryAdapter categoryAdapter;

    private RecyclerView toprateRecycler;

    private HomeUpcomingAdapter topRatedAdapter;

    private RecyclerView upComingRecycler;
    private HomeUpcomingAdapter upComingAdapter;

    //Khởi tạo ViewModel
    private HomeViewModels homeVMs = new HomeViewModels();

    private HomeSliderAdapter sliderAdapter;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager topratelayoutManager;
    private LinearLayoutManager upComminglayoutManager;
    private BottomSheetAdapter bottomSheetAdapter;
    private BottomSheetDialog dialog;
    private RecyclerView bottomRCV;
    private ExecutorService executors;
    private TextView categoryName;
    View dialogview;

    private boolean isBottomDialogShowing = false;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gắn ViewModel
        homeVMs = new ViewModelProvider(this).get(HomeViewModels.class);
        executors = Executors.newFixedThreadPool(3);
        Log.d("ListByCategory", homeVMs.getListMovieByCategory().toString());


    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Setup Category recycler
        categoryRecycler = root.findViewById(R.id.categoryRcv);
        toprateRecycler = root.findViewById(R.id.top_ratedRcv);
        upComingRecycler = root.findViewById(R.id.upcomingRcv);
        upComingRecycler.hasFixedSize();

        layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        topratelayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        upComminglayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);

        //Setup Slide
        sliderView = root.findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObservePopularChange();
        ObserveCategoryChange(this);
        ObserveTopRateChange(this);
        ObserveUpcomingChange(this);

    }

    //Observe data change
    private void ObservePopularChange() {

        //Listen to Livedata listPopular change
        homeVMs.getListPopular().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                sliderAdapter = new HomeSliderAdapter(getParentFragment().getContext(), movies);
                sliderView.setSliderAdapter(sliderAdapter);
            }
        });


    }

    private void ObserveUpcomingChange(RcvInterfce rcvInterfce) {
        homeVMs.getListUpcoming().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                upComingRecycler.setLayoutManager(upComminglayoutManager);
                upComingAdapter = new HomeUpcomingAdapter(movies, rcvInterfce);
                upComingRecycler.setAdapter(upComingAdapter);
            }
        });
    }

    private void ObserveCategoryChange(RcvInterfce rcvInterfce) {
        //Listen to Livedata listCategory change
        homeVMs.getListCategory().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryRecycler.setLayoutManager(topratelayoutManager);
                categoryAdapter = new HomeCategoryAdapter(getParentFragment().getContext(), categories, rcvInterfce);
                categoryRecycler.setAdapter(categoryAdapter);
            }

        });
    }

    private void ObserveTopRateChange(RcvInterfce rcvInterfce) {
        homeVMs.getListHomeTopRate().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                toprateRecycler.setLayoutManager(layoutManager);
                topRatedAdapter = new HomeUpcomingAdapter(movies, rcvInterfce);
                toprateRecycler.setAdapter(topRatedAdapter);


            }
        });
    }

    private void ObserveListByCategoryChange(RcvInterfce rcvInterfce, String name) {
        homeVMs.getListMovieByCategory().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                showBottomDialog(movies, rcvInterfce, name);
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    public void showBottomDialog(List<Movie> list, RcvInterfce rcvInterfce, String name) {


        dialogview = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, null);
        dialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isBottomDialogShowing = false;
                homeVMs.setNull();
            }
        });
        bottomRCV = dialogview.findViewById(R.id.rcv_movie_category);
        categoryName = dialogview.findViewById(R.id.category_name);
        categoryName.setText(name);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        bottomSheetAdapter = new BottomSheetAdapter(list, rcvInterfce);
        bottomRCV.setAdapter(bottomSheetAdapter);
        homeVMs.getListMovieByCategory().removeObservers(getViewLifecycleOwner());
        bottomRCV.setLayoutManager(gridLayoutManager);
        dialog.setContentView(dialogview);
        // Show the dialog
        dialog.show();
        isBottomDialogShowing = true;


        Log.d("bottomSheet", "show");
    }


    @Override
    public void onMovieClick(Movie movie, int idMovie) {
        int id = movie.getId();
        NavDirections action = HomeDirections.actionHome2ToMovieDetailFragment(id);
        Navigation.findNavController(getActivity(), R.id.host_fragment).navigate(action);
        if (isBottomDialogShowing) {
            dialog.dismiss();
            isBottomDialogShowing = false;
        }

    }

    @Override
    public void onMovieFavorClick(MovieDetail movieDetail) {

    }

    @Override
    public void onCategoryClick(Category category) {
        int id = category.getId();
        homeVMs.loadListMovieByCategory(id, Credential.apiKey);
        homeVMs.getListMovieByCategory().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null && !movies.isEmpty()) {
                    showBottomDialog(movies, Home.this, category.getName());
                }
            }
        });


    }


}