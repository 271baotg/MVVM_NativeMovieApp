package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeHorizontalAdapter extends RecyclerView.Adapter<HomeHorizontalAdapter.HorizontalViewHolder> {

    List<Movie> mdata;
    Context mcontex;

    public HomeHorizontalAdapter(List<Movie> mdata, Context mcontex) {
        this.mdata = mdata;
        this.mcontex = mcontex;
    }

    @NotNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.homemoviecard_item, parent, false);
        return new HorizontalViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HorizontalViewHolder holder, int position) {
        Movie movie = mdata.get(position);
        if (movie != null)
            Picasso.get()
                    .load(Credential.imgBaseUrl + movie.getPoster_path())
                    .fit()
                    .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (mdata != null)
            return 10;
        else return 0;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public HorizontalViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.movie_card);


        }
    }
}
