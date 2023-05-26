package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.Model.Movies;
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>{
    private List<MovieDetail> mdata;
    private RcvInterfce mrcvInterfce;

    public FavoriteMovieAdapter(List<MovieDetail> mdata,RcvInterfce rcvInterfce) {
        this.mdata = mdata;
        mrcvInterfce = rcvInterfce;
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritemoviecard_item,parent,false);
        return new FavoriteMovieViewHolder(root,mrcvInterfce);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, int position) {
        MovieDetail movie = mdata.get(position);
        if(movie == null)
        {
            Log.d("TestNullFavor", "null");
        }
        if (movie != null) {
            Picasso.get()
                    .load(Credential.imgBaseUrl + movie.getPoster_path())
                    .fit()
                    .into(holder.img);
            holder.title.setText(movie.getTitle());
            float score = movie.getVote_average();
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
            holder.ratingBar.setScore(starCount);

            holder.score.setText(String.valueOf(movie.getVote_average()));
            holder.overview.setText(movie.getOverview());
        }
    }

    @Override
    public int getItemCount() {
        return mdata != null ? mdata.size() : 0;
    }

    public MovieDetail getCurrent(int position) {
        return mdata.get(position);
    }

    public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        RatingBar ratingBar;
        TextView score;
        TextView overview;
        public FavoriteMovieViewHolder(@NonNull View itemView, RcvInterfce rcvInterfce) {
            super(itemView);

            img = itemView.findViewById(R.id.favor_image);
            title = itemView.findViewById(R.id.favor_title);
            ratingBar = itemView.findViewById(R.id.favor_rating);
            score = itemView.findViewById(R.id.favor_score);
            overview= itemView.findViewById(R.id.favor_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rcvInterfce != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            rcvInterfce.onMovieFavorClick(getCurrent(position));
                    }
                }
            });
        }
    }
}
