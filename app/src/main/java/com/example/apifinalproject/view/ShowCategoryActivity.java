package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.apifinalproject.R;
import com.example.apifinalproject.adapter.category_adapter.CategoryAdapter;
import com.example.apifinalproject.api.controller.CategoryApiController;
import com.example.apifinalproject.databinding.ActivityShowCategoryBinding;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.interfaces.ClickCategoryListener;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.model.Category;

import java.util.ArrayList;
import java.util.List;

public class ShowCategoryActivity extends AppCompatActivity implements ClickCategoryListener , View.OnClickListener {
    ActivityShowCategoryBinding binding;
    CategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityShowCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }



    @Override
    protected void onStart() {
        super.onStart();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        initializeView();
        getCategory();
    }

    private void initializeView(){
        initializeAdapter();
        setOnClickListener();
    }

    private void setOnClickListener(){
        binding.ivBackCategory.setOnClickListener(this::onClick);
    }

    private void initializeAdapter(){
        adapter = new CategoryAdapter(new ArrayList<>(),this::onClickCategory);
        binding.rv.setAdapter(adapter);
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
    }



    private void getCategory(){
        CategoryApiController controller = new CategoryApiController();
        controller.getCategory(new ListCallback<Category>() {
            @Override
            public void onSuccess(List<Category> list) {
                if (list.size() == 0){
                    binding.tvFound.setVisibility(View.VISIBLE);
                }
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.rv.setVisibility(View.VISIBLE);
                adapter.setCategories(list);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ShowCategoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickCategory(int id) {
        Intent intent = new Intent(getApplicationContext(),ShowOperationsCategoryActivity.class);
        intent.putExtra(KeysIntent.idCategoryIntent.name(),id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_category:
                onBackPressed();
                break;
        }
    }
}