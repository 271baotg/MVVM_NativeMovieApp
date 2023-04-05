package com.example.nativemovieapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nativemovieapp.Api.ApiService;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.TMDB;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Movies;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.viewmodel.HomeViewModels;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.Inflater;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder> {

    HomeViewModels homeVM;

    List<Category> mdata;
    Context mcontext;

    LifecycleOwner lifecycleOwner;

    public HomeCategoryAdapter(HomeViewModels homeVM, List<Category> mdata, Context mcontext, LifecycleOwner lifecycleOwner) {
        this.homeVM = homeVM;
        this.mdata = mdata;
        this.mcontext = mcontext;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homecategoryrow_item, parent, false);

        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mdata.get(position);
        int id = category.getId();
        if (category != null) {
            holder.categoryTitle.setText(category.getName());
            TMDB tmdb = ApiService.getTmdbApi();
            Call<Movies> call = tmdb.getListByCategory(Credential.apiKey, id);
            ExecutorService executor = Executors.newFixedThreadPool(10);
            Future<List<Movie>> listFuture = executor.submit(new Callable<List<Movie>>() {
                @Override
                public List<Movie> call() throws Exception {
                    return call.execute().body().getListMovie();
                }
            });
            try {
                Log.d("Trying to bind", listFuture.get().toString());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext, RecyclerView.HORIZONTAL, false);
                HomeHorizontalAdapter adapter = new HomeHorizontalAdapter(listFuture.get(), mcontext);
                holder.categoryRcv.setLayoutManager(linearLayoutManager);
                holder.categoryRcv.setAdapter(adapter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                executor.shutdown();
            }
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


