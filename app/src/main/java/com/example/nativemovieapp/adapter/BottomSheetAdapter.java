package com.example.nativemovieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder> {

    List<Movie> mdata;
    RcvInterfce rcvInterfce;

    public BottomSheetAdapter(List<Movie> mdata, RcvInterfce rcvInterfce) {
        this.mdata = mdata;
        this.rcvInterfce = rcvInterfce;
    }
    

    @Override
    public BottomSheetViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.similarmovie_item, parent, false);
        return new BottomSheetViewHolder(root, rcvInterfce);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BottomSheetAdapter.BottomSheetViewHolder holder, int position) {
        Movie movie = mdata.get(position);

        if (movie == null) return;
        Picasso.get().load(Credential.imgBaseUrl + movie.getPoster_path()).fit().into(holder.img);
        holder.score.setText(String.valueOf(movie.getVote_average()));
    }

    public Movie getCurrent(int position) {
        return mdata.get(position);
    }

    @Override
    public int getItemCount() {
        if (mdata != null) {
            return 10;
        }
        return 0;
    }

    public class BottomSheetViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView score;

        public BottomSheetViewHolder(@NotNull View itemView, RcvInterfce rcvInterfce) {
            super(itemView);
            img = itemView.findViewById(R.id.detail_image_similarMovie);
            score = itemView.findViewById(R.id.detail_score_similarMovie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rcvInterfce != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            rcvInterfce.onMovieClick(getCurrent(position), 0);
                        }
                    }
                }
            });
        }
    }
}


