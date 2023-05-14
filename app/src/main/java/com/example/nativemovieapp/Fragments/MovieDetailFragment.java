package com.example.nativemovieapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.DetailCategoryAdapter;
import com.example.nativemovieapp.viewmodel.FavoriteViewModel;
import com.example.nativemovieapp.viewmodel.MovieDetailViewModel;
import com.example.nativemovieapp.adapter.DetailMovieViewPagerAdapter;
import com.example.nativemovieapp.adapter.HomeCategoryAdapter;
import com.example.nativemovieapp.viewmodel.MovieDetailViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;


public class MovieDetailFragment extends Fragment {


    private MovieDetailViewModel detailVM;
    private FavoriteViewModel favVM;

    private RecyclerView categoryRCV;

    private TextView title;
    private TextView overview;
    private ImageView image;
    private RatingBar rating;

    private TextView detail_score;
    private LikeButton btnFavorite;

    private TabLayout mtablayout;

    private ViewPager2 mviewpager;
    private DetailMovieViewPagerAdapter mdetailmovieviewpageradapter;


    private Fragment fragment = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailVM = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        favVM = new ViewModelProvider(this).get(FavoriteViewModel.class);
        MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
        detailVM.loadMovieDetail(args.getId(), Credential.apiKey);
        detailVM.setId(args.getId());
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
        detail_score = root.findViewById(R.id.detail_score);
        categoryRCV = root.findViewById(R.id.detail_genresRCV);
        MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
        btnFavorite = root.findViewById(R.id.btn_favorite);
        btnFavorite.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
                detailVM.addToFavoriteList(args.getId());
                favVM.loadListFavor();

            }
            @Override
            public void unLiked(LikeButton likeButton) {
                MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
                detailVM.removeFromFavoriteList(args.getId());
                favVM.loadListFavor();
            }
        });
        detailVM.setFavoriteState(args.getId(),btnFavorite);

        mtablayout = root.findViewById(R.id.tablayout_detailMovie);
        mviewpager = root.findViewById(R.id.viewpager_detaiMovie);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailVM.getMovieDetail().removeObservers(getViewLifecycleOwner());
        ObserveChange();
    }




    public void ObserveChange() {
        detailVM.getMovieDetail().removeObservers(getViewLifecycleOwner());
        Log.d("parent", this.toString());
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

                detail_score.setText(String.valueOf(movieDetail.getVote_average()));



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
                mdetailmovieviewpageradapter = new DetailMovieViewPagerAdapter(getActivity(),fragment,detailVM.getId());
                mviewpager.setAdapter(mdetailmovieviewpageradapter);
                mviewpager.setUserInputEnabled(false);
                new TabLayoutMediator(mtablayout, mviewpager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                        switch(position){
                            case 0:
                                tab.setText("Trailers");
                                break;
                            case 1:
                                tab.setText("Images");
                                break;
                            case 2:
                                tab.setText("Similar");
                                break;
                        }
                    }
                }).attach();
            }
        });
    }


}