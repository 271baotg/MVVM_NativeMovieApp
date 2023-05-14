package com.example.nativemovieapp.adapter;

import android.content.Context;
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
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TopRateAdapter extends RecyclerView.Adapter<TopRateAdapter.TopRateViewHolder>{
    private List<Movie> mdata;
    Context context;
    private final RcvInterfce rcvInterfce;

    public TopRateAdapter(Context context, List<Movie> movies,RcvInterfce rcvInterfce) {
        this.context=context;
        this.mdata=movies;
        this.rcvInterfce=rcvInterfce;
    }

    @NotNull
    @Override
    public TopRateViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toprate_item,parent,false);
        return new TopRateViewHolder(view,rcvInterfce);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopRateViewHolder holder, int position) {
        Movie movie = mdata.get(position);

        if(movie==null)return;
        Picasso.get().load(Credential.imgBaseUrl+movie.getPoster_path()).fit().into(holder.searchImage);
        if(movie.getOriginal_language().equals("vi"))
        {
            holder.searchTitle.setText(movie.getOriginal_title());
        }
        else{
            holder.searchTitle.setText(movie.getTitle());
        }
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

    public Movie getCurrent(int position) {
        return mdata.get(position);
    }

    public class TopRateViewHolder extends RecyclerView.ViewHolder {
        private ImageView searchImage;
        private TextView searchTitle;
        private TextView searchScore;
        private TextView year;
        private RatingBar ratingBar;
        public TopRateViewHolder( @NotNull View itemView,RcvInterfce rcvInterfce) {
            super(itemView);

            searchImage = itemView.findViewById(R.id.search_image_toprate);
            searchTitle = itemView.findViewById(R.id.search_title_toprate);
            searchScore = itemView.findViewById(R.id.search_score_toprate);
            year = itemView.findViewById(R.id.movie_year_toprate);
            ratingBar = itemView.findViewById(R.id.movie_rating_toprate);

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

