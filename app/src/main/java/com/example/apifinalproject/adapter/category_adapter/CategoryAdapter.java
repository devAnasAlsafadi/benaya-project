package com.example.apifinalproject.adapter.category_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.databinding.CustomeCategoryBinding;
import com.example.apifinalproject.interfaces.ClickCategoryListener;
import com.example.apifinalproject.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    List<Category> categories ;
    ClickCategoryListener categoryListener;


    public void setCategoryListener(ClickCategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    public CategoryAdapter(List<Category> categories , ClickCategoryListener categoryListener) {
        this.categories = categories;
        this.categoryListener = categoryListener;
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyItemRangeInserted(0,categories.size());
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomeCategoryBinding binding = CustomeCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryHolder(binding,categoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.setData(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}