package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DetailCategoryAdapter extends RecyclerView.Adapter<DetailCategoryAdapter.CategoryViewHolder> {

    private List<Category> mdata;
    private Context mcontext;

    public DetailCategoryAdapter(List<Category> mdata, Context mcontext) {
        this.mdata = mdata;
        this.mcontext = mcontext;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailcategory_item, parent, false);
        return new CategoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {
        Category item = mdata.get(position);
        if (item != null) {
            holder.category.setText(item.getName());
        }
        switch (item.getId()) {
            case 28:
                Drawable action = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_action);
                holder.container.setBackground(action);
                break;
            case 12:
                Drawable adventure = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_adventure);
                holder.container.setBackground(adventure);
                break;
            case 16:
                Drawable animation = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_animation);
                holder.container.setBackground(animation);
                break;
            case 35:
                Drawable comedy = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_comedy);
                holder.container.setBackground(comedy);
                break;
            case 80:
                Drawable crime = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_crime);
                holder.container.setBackground(crime);
                break;
            case 99:
                Drawable documentary = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_documentation);
                holder.container.setBackground(documentary);
                break;
            case 18:
                Drawable drama = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_drama);
                holder.container.setBackground(drama);
                break;
            case 10751:
                Drawable family = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_family);
                holder.container.setBackground(family);
                break;
            case 36:
                Drawable history = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_history);
                holder.container.setBackground(history);
                break;
            case 27:
                Drawable horror = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_horror);
                holder.container.setBackground(horror);
                break;
            case 14:
                Drawable fantasy = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_fantasy);
                holder.container.setBackground(fantasy);
                break;
            case 10402:
                Drawable music = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_music);
                holder.container.setBackground(music);
                break;
            case 9648:
                Drawable mystery = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_mystery);
                holder.container.setBackground(mystery);
                break;
            case 10749:
                Drawable romance = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_romance);
                holder.container.setBackground(romance);
                break;
            case 878:
                Drawable sciencefiction = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_sciencefiction);
                holder.container.setBackground(sciencefiction);
                break;
            case 10770:
                Drawable tvmovie = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_tvmovie);
                holder.container.setBackground(tvmovie);
                break;
            case 53:
                Drawable thriller = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_thriller);
                holder.container.setBackground(thriller);
                break;
            case 10752:
                Drawable war = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_war);
                holder.container.setBackground(war);
                break;
            case 37:
                Drawable western = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_western);
                holder.container.setBackground(western);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mdata != null ? mdata.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView category;
        private LinearLayout container;

        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_text);
            container = itemView.findViewById(R.id.category_container);
        }
    }
}
