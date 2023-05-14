package com.example.nativemovieapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeTopRatedAdapter extends RecyclerView.Adapter<HomeTopRatedAdapter.HorizontalViewHolder> {


    List<Movie> mdata;

    private final RcvInterfce rcvInterfce;

    public HomeTopRatedAdapter(List<Movie> mdata, RcvInterfce rcvInterfce) {

        this.mdata = mdata;
        this.rcvInterfce = rcvInterfce;

    }


    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.homemoviecard_item, parent, false);
        return new HorizontalViewHolder(root, rcvInterfce);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HorizontalViewHolder holder, int position) {
        Movie movie = mdata.get(position);
        if (movie != null) {
            Log.d("insidechild", movie.toString());
            Picasso.get()
                    .load(Credential.imgBaseUrl + movie.getPoster_path())
                    .fit()
                    .into(holder.img);
        }
        if (mdata == null)
            Log.d("insidechild", "movie null");
    }

    @Override
    public int getItemCount() {
        return mdata != null ? mdata.size() : 0;
    }

    public Movie getCurrent(int position) {
        return mdata.get(position);
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public HorizontalViewHolder(@NonNull @NotNull View itemView, RcvInterfce rcvInterfce) {
            super(itemView);

            img = itemView.findViewById(R.id.movie_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rcvInterfce != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            rcvInterfce.onMovieClick(getCurrent(position),0);
                    }
                }
            });


        }
    }
}
