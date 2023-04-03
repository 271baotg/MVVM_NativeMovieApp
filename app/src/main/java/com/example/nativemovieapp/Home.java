package com.example.nativemovieapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;

import com.example.nativemovieapp.adapter.HomeSliderAdapter;
import com.example.nativemovieapp.databinding.FragmentHomeBinding;
import com.example.nativemovieapp.viewmodel.HomeViewModels;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class Home extends Fragment {

    private SliderView sliderView;

    //Khởi tạo ViewModel
    private HomeViewModels homeVMs = new HomeViewModels();

    private HomeSliderAdapter sliderAdapter;
    private Button loadData;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gắn ViewModel
        homeVMs = new ViewModelProvider(this).get(HomeViewModels.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentHomeBinding binding = DataBindingUtil.bind(root);
        binding.setViewModel(homeVMs);
        loadData = root.findViewById(R.id.testButton);
        //Setup Slide
        sliderView = root.findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        homeVMs.loadListPopularMovie();
        ObserveChange();
        return root;
    }

    //Observe data change
    private void ObserveChange() {
        homeVMs.getListPopular().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                sliderAdapter = new HomeSliderAdapter(getParentFragment().getContext(), movies);
                sliderView.setSliderAdapter(sliderAdapter);
            }
        });
    }

}