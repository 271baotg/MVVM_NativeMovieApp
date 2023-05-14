package com.example.nativemovieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.FullscreenTrailer;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieTrailer;
import com.example.nativemovieapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>{
    private List<MovieTrailer> ListTrailers;
    private Context Context;

    public TrailersAdapter(List<MovieTrailer> listTrailers,Context context) {
        ListTrailers = listTrailers;
        Context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers_item,parent,false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrailersViewHolder holder, int position) {
        MovieTrailer movieTrailer = ListTrailers.get(position);

        holder.title.setText(movieTrailer.getName());
        Log.d("titleTrailer", movieTrailer.getName());

        String date = movieTrailer.getPublished_at().substring(0,10);

        holder.date.setText(date);
        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(movieTrailer.getKey(), 0);
                //youTubePlayer.loadVideo(movieTrailer.getKey(),0); // auto play

            }
        });

    }
    @Override
    public int getItemCount() {
        if(ListTrailers.size() > 10)
        {
            return 10;
        }
        else return ListTrailers.size();
    }

    public MovieTrailer getCurrent(int position) {
        return ListTrailers.get(position);
    }
    public void Release(){
        Context = null;
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder{
        YouTubePlayerView youTubePlayerView;
        TextView title;
        TextView date;

        @SuppressLint("ClickableViewAccessibility")
        public TrailersViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            title = itemView.findViewById(R.id.trailer_title);
            date = itemView.findViewById(R.id.trailer_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(Context, FullscreenTrailer.class);
                    intent.putExtra("linkTrailer",getCurrent(position).getKey());
                    Context.startActivity(intent);
                }
            });
        }
    }
}
