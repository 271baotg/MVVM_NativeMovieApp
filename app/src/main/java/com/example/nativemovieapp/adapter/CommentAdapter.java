package com.example.nativemovieapp.adapter;

import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Model.Comment;
import com.example.nativemovieapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> data;

    public CommentAdapter(List<Comment> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentViewHolder holder, int position) {
        Comment comment = data.get(position);
        if (comment != null && !comment.getAvatar().equals("null")) {
            holder.context.setText(comment.getContent());
            holder.username.setText(comment.getName());
//            holder.timestamp.setText(comment.getTimestamp());
            holder.timestamp.setText(setTime(comment.getTimestamp()));
            Picasso.get().load(URI.create(comment.getAvatar()).toString()).fit().into(holder.img);
        } else {
            holder.context.setText(comment.getContent());
            holder.username.setText(comment.getName());
//            holder.timestamp.setText(comment.getTimestamp());
            holder.timestamp.setText(setTime(comment.getTimestamp()));
            holder.img.setImageResource(R.drawable.account_icon);
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public String setTime(String time) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd-MM-yyyy");

// Convert the timestamp string to LocalDateTime
        LocalDateTime timestamp = LocalDateTime.parse(time, formatter);

// Convert LocalDateTime to Date
        Date date = Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());

// Get the "time ago" representation using DateUtils.getRelativeTimeSpanString()
        String niceDateStr = DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();

        return niceDateStr;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView username;
        TextView timestamp;
        TextView context;

        public CommentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.comment_avatar);
            username = itemView.findViewById(R.id.comment_name);
            timestamp = itemView.findViewById(R.id.comment_time);
            context = itemView.findViewById(R.id.comment_context);
        }
    }
}
