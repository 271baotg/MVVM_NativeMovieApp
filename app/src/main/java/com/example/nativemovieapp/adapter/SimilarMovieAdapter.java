package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Fragments.MovieDetailFragment;
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.PendingIntent.getActivities;
import static android.app.PendingIntent.getActivity;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieViewHolder> {
    private List<Movie> mdata;
    Context context;
    private final RcvInterfce rcvInterfce;



    public SimilarMovieAdapter(Context context, List<Movie> movies,RcvInterfce rcvInterfce) {
        this.context=context;
        this.mdata=movies;
        this.rcvInterfce=rcvInterfce;

    }

    @NotNull
    @Override
    public SimilarMovieViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similarmovie_item,parent,false);
        return new SimilarMovieViewHolder(view,rcvInterfce);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SimilarMovieViewHolder holder, int position) {
        Movie movie = mdata.get(position);

        if(movie==null)return;
        Picasso.get().load(Credential.imgBaseUrl+movie.getPoster_path()).fit().into(holder.similarMovieImage);
        holder.similarMovieScore.setText(String.valueOf(movie.getVote_average()));
    }
    @Override
    public int getItemCount() {
//        Log.d("insideSimilar", String.valueOf(mdata.size()));
        if(mdata!=null)
        {
            return 10;
        }
        return 0;
    }

    public Movie getCurrent(int position) {
        return mdata.get(position);
    }

    public class SimilarMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView similarMovieImage;
        private TextView similarMovieScore;
        public SimilarMovieViewHolder( @NotNull View itemView,RcvInterfce rcvInterfce) {
            super(itemView);

            similarMovieImage = itemView.findViewById(R.id.detail_image_similarMovie);
            similarMovieScore = itemView.findViewById(R.id.detail_score_similarMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rcvInterfce != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            rcvInterfce.onMovieClick(getCurrent(position),0);
                        }

                    }
                }
            });
        }

    }
}
