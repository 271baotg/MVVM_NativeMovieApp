package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chaek.android.RatingBar;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.RoundedCornerTransformation;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.viewmodel.SearchViewModels;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.nativemovieapp.Api.LiveDataProvider.movieListFinal;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Movie> mdata;
     Context context;
     private final RcvInterfce rcvInterfce;

    public SearchAdapter(Context context, List<Movie> movies, RcvInterfce rcvInterfce) {
        this.context=context;
        this.mdata=movies;
        this.rcvInterfce=rcvInterfce;
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
        return new SearchViewHolder(view,rcvInterfce);
    }


    @Override
    public void onBindViewHolder( @NotNull SearchViewHolder holder, int position) {
        Movie movie = mdata.get(position);

        if(movie==null)return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            Picasso.get().load(Credential.imgBaseUrl+movie.getPoster_path()).transform(new RoundedCornerTransformation(32, 0)).fit().into(holder.searchImage);
        }
        holder.searchTitle.setText(movie.getTitle());
        holder.searchScore.setText(String.valueOf(movie.getVote_average()));
        holder.content.setText(movie.getOverview());
        Picasso.get().load(Credential.imgBaseUrl+movie.getPoster_path()).transform(new RoundedCornerTransformation(32, 0)).fit().into(holder.searchImage);
        if(movie.getOriginal_language().equals("vi"))
        {
            holder.searchTitle.setText(movie.getOriginal_title());
        }
        else{
            holder.searchTitle.setText(movie.getTitle());
        }
        holder.searchScore.setText(String.valueOf(movie.getVote_average()));
        holder.content.setText(movie.getOverview());
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
        try {
            // Chuyển chuỗi release_date thành đối tượng Date
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(movie.getRelease_date());
            // Định dạng lại đối tượng Date để lấy ra năm
            String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(date);
            // Hiển thị năm lên TextView
            holder.year.setText("("+year+")");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        if(mdata!=null)
        {
            return mdata.size();
        }
        return 0;
    }

    public Movie getCurrent(int position) {
        return mdata.get(position);
    }

    public  class SearchViewHolder extends RecyclerView.ViewHolder {

        private ImageView searchImage;
        private TextView searchTitle;
        private TextView searchScore;
        private TextView year;
        private RatingBar ratingBar;
        private TextView content;
        public SearchViewHolder( @NotNull View itemView,RcvInterfce rcvInterfce) {
            super(itemView);

            searchImage = itemView.findViewById(R.id.search_image);
            searchTitle = itemView.findViewById(R.id.search_title);
            searchScore = itemView.findViewById(R.id.search_score);
            year = itemView.findViewById(R.id.movie_year);
            ratingBar = itemView.findViewById(R.id.movie_rating);
            content = itemView.findViewById(R.id.search_content);

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
