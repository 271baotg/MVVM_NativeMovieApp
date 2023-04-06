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
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.nativemovieapp.Api.LiveDataProvider.movieListFinal;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>{
    private List<Movie> mdata;
    Context context;

    public UpcomingAdapter(Context context, List<Movie> movies) {
        this.context=context;
        this.mdata=movies;
    }
    @NotNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_item,parent,false);
        return new UpcomingViewHolder(view);
    }



    @Override
    public void onBindViewHolder( @NotNull UpcomingViewHolder holder, int position) {
        Movie movie = mdata.get(position);

        if(movie==null)return;
        Picasso.get().load(Credential.imgBaseUrl+movie.getPoster_path()).fit().into(holder.searchImage);
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
            return 10;
        }
        return 0;
    }



    public static class UpcomingViewHolder extends RecyclerView.ViewHolder {

        private ImageView searchImage;
        private TextView searchTitle;
        private TextView searchScore;
        private TextView year;
        private RatingBar ratingBar;
        public UpcomingViewHolder( @NotNull View itemView) {
            super(itemView);

            searchImage = itemView.findViewById(R.id.search_image_upcoming);
            searchTitle = itemView.findViewById(R.id.search_title_upcoming);
            searchScore = itemView.findViewById(R.id.search_score_upcoming);
            year = itemView.findViewById(R.id.movie_year_upcoming);
            ratingBar = itemView.findViewById(R.id.movie_rating_upcoming);
        }
    }


}
