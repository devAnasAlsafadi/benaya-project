package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.apifinalproject.R;
import com.example.apifinalproject.adapter.operationAdapter.OperationsAdapter;
import com.example.apifinalproject.api.controller.OperationsApiController;
import com.example.apifinalproject.databinding.ActivityShowOperationsBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.fragment.DeleteFragment;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.interfaces.DeleteFragmentListener;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.Types;
import com.example.apifinalproject.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class ShowOperationsActivity extends AppCompatActivity implements ActionTypeCallback , DeleteFragmentListener , View.OnClickListener {
    ActivityShowOperationsBinding binding;
    OperationsAdapter adapter;
    private DeleteFragment deleteFragment;
    private  int id ,positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    @Override
    protected void onStart() {
        super.onStart();
        controllerAnimation();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        initializeView();
        getOperations();
        adapter.setCallback(this::onActionDeleteUpdateCallback);


    }

    private void initializeView(){
        initializeAdapter();
        setOnClickListener();
    }

    private void initializeAdapter(){
        adapter = new OperationsAdapter(new ArrayList<>(),Types.operationProcess);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }





    private void getOperations(){
        OperationsApiController.getInstance().getOperations(new ListCallback<Operation>() {
            @Override
            public void onSuccess(List<Operation> list) {
                if (list.size() == 0){
                    binding.tvFound.setVisibility(View.VISIBLE);
                }
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.rv.setVisibility(View.VISIBLE);
                adapter.setList(list);

            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ShowOperationsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActionDeleteUpdateCallback(ActionType actionType, int id, int position) {
        this.id = id;
        this.positions = position;
        if (actionType.equals(ActionType.delete)){
            deleteFragment =DeleteFragment.newInstance();
            deleteFragment.show(getSupportFragmentManager(),null);

        } else if (actionType == ActionType.update){
            Intent intent = new Intent(getApplicationContext(),CreateOperationsActivity.class);
            intent.putExtra(KeysIntent.intentOperationsUpdatedCreated.name(),2);
            intent.putExtra(KeysIntent.idOperationsIntent.name(),id);
            startActivity(intent);
        }
    }
    private void controllerAnimation(){
        YoYo.with(Techniques.ZoomInUp).repeat(0).duration(1000).playOn(binding.fbAddOperations);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.RotateIn).repeat(0).duration(1000).playOn(binding.fbAddOperations);
            }
        },1000);
    }


    @Override
    public void deleteListener() {
        deleteOperations();
    }

    private void deleteOperations(){
        OperationsApiController.getInstance().deleteOperations(id, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(ShowOperationsActivity.this, message, Toast.LENGTH_SHORT).show();
                deleteFragment.dismiss();
                adapter.delete(positions);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ShowOperationsActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setOnClickListener(){
        binding.fbAddOperations.setOnClickListener(this::onClick);
        binding.ivBackOperations.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fb_add_operations:
                Intent intent = new Intent(getApplicationContext(),CreateOperationsActivity.class);
                intent.putExtra(KeysIntent.intentOperationsUpdatedCreated.name(),2);
                startActivity(intent);
                break;
            case R.id.iv_back_operations:
                onBackPressed();
                break;
        }
    }
}