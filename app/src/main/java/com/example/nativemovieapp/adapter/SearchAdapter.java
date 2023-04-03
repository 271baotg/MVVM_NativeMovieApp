package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.viewmodel.SearchViewModels;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.nativemovieapp.Api.LiveDataProvider.movieListFinal;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    private List<Movie> mdata;
    private List<Movie> mListOld;
     Context context;

    public SearchAdapter(Context context, List<Movie> movies, List<Movie> moviesOld) {
        this.context=context;
        this.mdata=movies;
        this.mListOld=moviesOld;
    }
//
//    public void setFilteredList(List<Movie> filteredList)
//    {
//        this.mdata=filteredList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @NotNull
    @Override
    public SearchViewHolder onCreateViewHolder( @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_iteam,parent,false);
        return new SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder( @NotNull SearchViewHolder holder, int position) {
        Movie movie = mdata.get(position);

        if(movie==null)return;
        Picasso.get().load(Credential.imgBaseUrl+movie.getImageURL()).fit().into(holder.searchImage);
        holder.searchTitle.setText(movie.getTitle());
        holder.searchScore.setText(String.valueOf(movie.getVote_average()));

        float rating = movie.getVote_average();

// Chuyển đổi điểm đánh giá thành số sao tương ứng
        float starCount = 0;
        if (rating >= 8.0f) {
            starCount = 5.0f;
        } else if (rating >= 6.0f) {
            starCount = 4.0f;
        } else if(rating >=4.0f){
            starCount = 3.0f;
        }
        else if(rating >=2.0f){
            starCount = 2.0f;
        }
        else {
            starCount = 1.0f;
        }

// Thực hiện đánh giá bằng cách đặt số sao cho đúng
        holder.ratingBar.setScore(starCount);
        holder.year.setText(movie.getRelease_date());
    }


    @Override
    public int getItemCount() {
        if(mdata!=null)
        {
            return mdata.size();
        }
        return 0;
    }



    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        private ImageView searchImage;
        private TextView searchTitle;
        private TextView searchScore;
        private TextView year;
        private RatingBar ratingBar;
        public SearchViewHolder( @NotNull View itemView) {
            super(itemView);

            searchImage = itemView.findViewById(R.id.search_image);
            searchTitle = itemView.findViewById(R.id.search_title);
            searchScore = itemView.findViewById(R.id.search_score);
            year = itemView.findViewById(R.id.movie_year);
            ratingBar = itemView.findViewById(R.id.movie_rating);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty())
                {
                    mdata=null;
                }
                else{
                    List<Movie> list = new ArrayList<>();
                    for (Movie item:movieListFinal){
                        if(item.getTitle().toLowerCase().contains(strSearch)){
                            list.add(item);
                        }
                    }
                    mdata=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=mdata;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mdata=(List<Movie>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
