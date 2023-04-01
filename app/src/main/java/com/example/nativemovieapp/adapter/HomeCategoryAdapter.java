package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder> {

    List<Category> mdata;
    Context mcontext;

    public HomeCategoryAdapter(Context mcontext, List<Category> mdata) {
        this.mdata = mdata;
        this.mcontext = mcontext;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homecategoryrow_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mdata.get(position);
        if (category == null)
            Log.d("incategoryadapter", "null ");
        else {
            Log.d("success", category.toString());
            holder.categoryTitle.setText(category.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mdata != null ? mdata.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        RecyclerView categoryRcv;
        TextView categoryTitle;

        public CategoryViewHolder(@NotNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.category_title);
            categoryRcv = itemView.findViewById(R.id.rowRcv);
        }
    }
}
