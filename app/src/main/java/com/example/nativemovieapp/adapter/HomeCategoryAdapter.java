package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Api.ApiService;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.TMDB;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Movies;
import com.example.nativemovieapp.R;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.Inflater;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder> {

    List<Category> mdata;
    Context mcontext;

    private final RcvInterfce rcvInterfce;

    public HomeCategoryAdapter(Context mcontext, List<Category> mdata, RcvInterfce rcvInterfce) {
        this.mdata = mdata;
        this.mcontext = mcontext;
        this.rcvInterfce = rcvInterfce;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homecategory_item, parent, false);
        return new CategoryViewHolder(view, rcvInterfce);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mdata.get(position);
        if (category != null) {
            holder.ctgTitle.setText(category.getName());
        }
        switch (category.getId()) {
            case 28:
                holder.ctgImg.setImageResource(R.drawable.action_background);
                break;
            case 12:
                holder.ctgImg.setImageResource(R.drawable.adventure_background);
                break;
            case 16:
                holder.ctgImg.setImageResource(R.drawable.animation_background);
                break;
            case 35:
                holder.ctgImg.setImageResource(R.drawable.comedy_background);
                break;
            case 80:
                holder.ctgImg.setImageResource(R.drawable.crime_background);
                break;
            case 99:
                holder.ctgImg.setImageResource(R.drawable.documentary_background);
                break;
            case 18:
                holder.ctgImg.setImageResource(R.drawable.drama_background);
                break;
            case 10751:
                holder.ctgImg.setImageResource(R.drawable.family_background);
                break;
            case 36:
                holder.ctgImg.setImageResource(R.drawable.history_theme);
                break;
            case 27:
                holder.ctgImg.setImageResource(R.drawable.horror_background);
                break;
            case 14:
                holder.ctgImg.setImageResource(R.drawable.fantasy_background);
                break;
            case 10402:
                holder.ctgImg.setImageResource(R.drawable.music_background);
                break;
            case 9648:
                holder.ctgImg.setImageResource(R.drawable.mystery_background);
                break;
            case 10749:
                holder.ctgImg.setImageResource(R.drawable.romance_background);
                break;
            case 878:
                holder.ctgImg.setImageResource(R.drawable.science_background);
                break;
            case 10770:
                holder.ctgImg.setImageResource(R.drawable.tvmovie_background);
                break;
            case 53:
                holder.ctgImg.setImageResource(R.drawable.thriller_background);
                break;
            case 10752:
                holder.ctgImg.setImageResource(R.drawable.war_background);
                break;
            case 37:
                holder.ctgImg.setImageResource(R.drawable.western_background);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mdata != null ? 15 : 0;
    }

    public Category getCurrent(int position) {
        return mdata.get(position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        RecyclerView ctgRcv;
        TextView ctgTitle;

        ImageView ctgImg;


        public CategoryViewHolder(@NotNull View itemView, RcvInterfce rcvInterfce) {
            super(itemView);
            ctgTitle = itemView.findViewById(R.id.category_title);
            ctgRcv = itemView.findViewById(R.id.categoryRcv);
            ctgImg = itemView.findViewById(R.id.category_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rcvInterfce != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            rcvInterfce.onCategoryClick(getCurrent(position));
                    }
                }
            });

        }
    }

    public void release() {
        this.mcontext = null;
    }


}
