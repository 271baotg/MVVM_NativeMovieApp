package com.example.nativemovieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.R;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;


import java.util.List;


public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.HomeSliderViewHolder> {

    private List<Movie> mdata;
    Context mcontext;


    public HomeSliderAdapter(Context mcontext, List<Movie> mdata) {
        this.mdata = mdata;
        this.mcontext = mcontext;
    }

    @Override
    public HomeSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeslider_item, parent, false);


        return new HomeSliderViewHolder(root);
    }

    @Override
    public void onBindViewHolder(HomeSliderViewHolder viewHolder, int position) {
        Movie movie = mdata.get(position);
        if (movie == null)
            return;
        else {
            Picasso.get()
                    .load(Credential.imgBaseUrl + movie.getPoster_path())
                    .fit()
                    .into(viewHolder.img);
            //Glide
//            Glide.with(mcontext)
//                    .load(Credential.imgBaseUrl + movie.getPoster_path())
//                    .centerCrop()
//                    .into(viewHolder.img);
            String point = String.valueOf(movie.getVote_average());
            if(movie.getOriginal_language().equals("vi"))
            {
                viewHolder.title.setText(movie.getOriginal_title());
            }
            else{
                viewHolder.title.setText(movie.getTitle());
            }
            viewHolder.year.setText(movie.getRelease_date());
            viewHolder.point.setText(point);
            float rating = movie.getVote_average();

// Chuyển đổi điểm đánh giá thành số sao tương ứng
            float starCount = 0;
            if (rating >= 8.0f) {
                starCount = 5.0f;
            } else if (rating >= 6.0f) {
                starCount = 4.0f;
            } else if (rating >= 4.0f) {
                starCount = 3.0f;
            } else if (rating >= 2.0f) {
                starCount = 2.0f;
            } else {
                starCount = 1.0f;
            }

// Thực hiện đánh giá bằng cách đặt số sao cho đúng
            viewHolder.ratingBar.setScore(starCount);
        }
    }

    @Override
    public int getCount() {
        if (mdata != null)
            return 6;
        else return 0;
    }

    public class HomeSliderViewHolder extends SliderViewAdapter.ViewHolder {


        private ImageView img;
        private BlurView blurView;
        private TextView title;
        private TextView year;
        private RatingBar ratingBar;
        private TextView point;

        @SuppressLint("ResourceAsColor")
        public HomeSliderViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.slide_img);
            blurView = itemView.findViewById(R.id.blurView);
            FrameLayout frameLayout = itemView.findViewById(R.id.image_container);
            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            ratingBar = itemView.findViewById(R.id.movie_rating);
            point = itemView.findViewById(R.id.movie_point);


            // Set up the BlurView with the ImageView
            ViewGroup rootView = (ViewGroup) itemView.getRootView();
            Drawable windowBackground = itemView.getBackground();
            blurView.setupWith(rootView)
                    .setBlurRadius(10f)
                    .setOverlayColor(R.color.accent)
                    .setBlurAutoUpdate(true)
                    .setFrameClearDrawable(windowBackground);


        }
    }
}

