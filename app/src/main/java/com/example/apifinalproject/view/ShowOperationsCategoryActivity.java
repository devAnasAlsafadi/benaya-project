package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.apifinalproject.adapter.operationAdapter.OperationsAdapter;
import com.example.apifinalproject.api.controller.CategoryApiController;
import com.example.apifinalproject.databinding.ActivityShowOperationsCategoryBinding;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.Types;
import com.example.apifinalproject.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class ShowOperationsCategoryActivity extends AppCompatActivity {
    ActivityShowOperationsCategoryBinding binding;
    OperationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowOperationsCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCategoryById();

        binding.ivBackOperationsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private int getId(){
        return getIntent().getIntExtra(KeysIntent.idCategoryIntent.name(),0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        initializeView();


    }

    private void initializeView(){
        initializeAdapter();
    }

    private void initializeAdapter(){
        adapter = new OperationsAdapter(new ArrayList<>(),Types.categoryProcess);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }





    private void getCategoryById(){
        CategoryApiController controller = new CategoryApiController();
        controller.getOperationsByCategoryId(String.valueOf(getId()), new ListCallback<Operation>() {
            @Override
            public void onSuccess(List<Operation> list) {
                if (list.size() == 0){
                    binding.tvFound.setVisibility(View.VISIBLE);
                }
                if (list.size()!=0){
                    binding.tvTitle.setText(list.get(0).categoryName);
                }
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.rv.setVisibility(View.VISIBLE);
                adapter.setList(list);
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(ShowOperationsCategoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}