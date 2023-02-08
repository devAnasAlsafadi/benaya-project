package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.EmployeeApiController;
import com.example.apifinalproject.adapter.employee_adapter.EmployeeAdapter;
import com.example.apifinalproject.databinding.ActivityShowEmployeeBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.fragment.DeleteFragment;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.interfaces.DeleteFragmentListener;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class ShowEmployeeActivity extends AppCompatActivity implements ActionTypeCallback, View.OnClickListener, DeleteFragmentListener {
    ActivityShowEmployeeBinding binding;
    EmployeeAdapter adapter;
    int id,position;
    private DeleteFragment deleteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityShowEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    private void controllerAnimation(){
        YoYo.with(Techniques.ZoomInUp).repeat(0).duration(1000).playOn(binding.fbAdd);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.RotateIn).repeat(0).duration(1000).playOn(binding.fbAdd);
            }
        },1000);
    }


    @Override
    protected void onStart() {
        super.onStart();
        controllerAnimation();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        setOnClickListener();

        inilializeAdapter();
        getEmployee();
    }

    private void inilializeAdapter(){
        adapter = new EmployeeAdapter(new ArrayList<>(),this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }



    private void getEmployee(){

        EmployeeApiController.getEmployees(new ListCallback<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                if (list.size() == 0){
                    binding.tvFound.setVisibility(View.VISIBLE);
                }
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.rv.setVisibility(View.VISIBLE);
                adapter.setList(list);
            }

            @Override
            public void onFailure(String message) {
                Log.d("TAG", "onFailure: "+message);

            }
        });

    }

    @Override
    public void onActionDeleteUpdateCallback(ActionType actionType, int id, int position) {
        this.id = id;
        this.position = position;
        if (actionType == ActionType.delete){
            deleteFragment = DeleteFragment.newInstance();
            deleteFragment.show(getSupportFragmentManager(),null);
        }
        else if (actionType == ActionType.update){
            Intent intent = new Intent(getApplicationContext(),CreateEmployeeActivity.class);
            intent.putExtra(KeysIntent.intentEmployeeUpdatedCreated.name(),2);
            intent.putExtra(KeysIntent.idUpdatedEmployee.name(),id);
            startActivity(intent);
        }
    }

    private void setOnClickListener(){
        binding.fbAdd.setOnClickListener(this::onClick);
        binding.ivBackEmployee.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fb_add:
                Intent intent = new Intent(getApplicationContext(),CreateEmployeeActivity.class);
                intent.putExtra(KeysIntent.intentEmployeeUpdatedCreated.name(),1);
                startActivity(intent);
                break;
            case R.id.iv_back_employee:
                onBackPressed();
                break;

        }
    }



    @Override
    public void deleteListener() {
        Log.d("TAG", "deleteListener: "+position);
        deleteEmployee();

    }


    private void deleteEmployee(){
        EmployeeApiController controller = new EmployeeApiController();
        controller.deleteEmployee(id, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                deleteFragment.dismiss();
                adapter.delete(position);
                Toast.makeText(ShowEmployeeActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ShowEmployeeActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

    }


}