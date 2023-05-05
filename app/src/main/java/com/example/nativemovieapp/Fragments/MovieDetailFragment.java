package com.example.nativemovieapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.bumptech.glide.Glide;
import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.DetailCategoryAdapter;
import com.example.nativemovieapp.adapter.HomeCategoryAdapter;
import com.example.nativemovieapp.viewmodel.MovieDetailViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


public class MovieDetailFragment extends Fragment {

    private MovieDetailViewModel detailVM;

    private RecyclerView categoryRCV;

    private TextView title;
    private TextView overview;
    private ImageView image;
    private RatingBar rating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailVM = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
        detailVM.loadMovieDetail(args.getId(), Credential.apiKey);

        Log.d("id of movie", args.toString());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frament_movie_detail, container, false);
        image = root.findViewById(R.id.detail_image);
        title = root.findViewById(R.id.detail_title);
        rating = root.findViewById(R.id.detail_rating);
        overview = root.findViewById(R.id.detail_overview);
        categoryRCV = root.findViewById(R.id.detail_genresRCV);


        ObserveChange();
        return root;
    }


    public void ObserveChange() {
        detailVM.getMovieDetail().observe(getViewLifecycleOwner(), new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {

                Log.d("Detail change", movieDetail.toString());

                //Load Image
                Glide.with(getContext())
                        .load(Credential.imgBaseUrl + movieDetail.getImageURL())
                        .into(image);
                //Load Title
                title.setText(movieDetail.getTitle());
                //Rating

                float score = movieDetail.getVote_average();
                float starCount = 0;
                if (score >= 8.0f) {
                    starCount = 5.0f;
                } else if (score >= 6.0f) {
                    starCount = 4.0f;
                } else if (score >= 4.0f) {
                    starCount = 3.0f;
                } else if (score >= 2.0f) {
                    starCount = 2.0f;
                } else {
                    starCount = 1.0f;
                }
                rating.setScore(starCount);

                //Category lane

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                if (movieDetail.getGenres().size() > 2) {
                    categoryRCV.setLayoutManager(staggeredGridLayoutManager);
                } else categoryRCV.setLayoutManager(linearLayoutManager);
                DetailCategoryAdapter adapter = new DetailCategoryAdapter(movieDetail.getGenres(), getContext());
                categoryRCV.setAdapter(adapter);

                //Overview
                overview.setText(movieDetail.getOverview());

            }
        });
    }
}