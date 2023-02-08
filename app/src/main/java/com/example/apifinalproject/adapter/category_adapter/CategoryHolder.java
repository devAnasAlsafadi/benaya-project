package com.example.apifinalproject.adapter.category_adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.R;
import com.example.apifinalproject.databinding.CustomeCategoryBinding;
import com.example.apifinalproject.interfaces.ClickCategoryListener;
import com.example.apifinalproject.model.Category;

public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CustomeCategoryBinding binding;
    ClickCategoryListener categoryListener;
    int id;

    public CategoryHolder(CustomeCategoryBinding binding,ClickCategoryListener categoryListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.categoryListener = categoryListener;

        setOnClickListener();

    }

    public void setData(Category category){
        binding.tvTitle.setText(category.name);
        this.id = category.id;
    }


    private void setOnClickListener(){
        binding.constraintCategory.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.constraint_category:
                categoryListener.onClickCategory(id);
                break;
        }
    }
}
