package com.example.apifinalproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.AuthApiController;
import com.example.apifinalproject.databinding.ActivityHomeAdminBinding;
import com.example.apifinalproject.interfaces.ProcessCallback;

public class HomeAdminActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityHomeAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);

    }


    private void controllerAnimation(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.RotateIn).repeat(0).duration(2000).playOn(binding.constraintCategory);
                YoYo.with(Techniques.RotateIn).repeat(0).duration(2000).playOn(binding.constraintDeclarations);
                YoYo.with(Techniques.RotateIn).repeat(0).duration(2000).playOn(binding.constraintEmployee);
                YoYo.with(Techniques.RotateIn).repeat(0).duration(2000).playOn(binding.constraintUser);
                YoYo.with(Techniques.RotateIn).repeat(0).duration(2000).playOn(binding.constraintOperations);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:
                logout();
                break;
            case R.id.change_password:
                Intent intent = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        controllerAnimation();
        setOnClickListener();
    }

    private void setOnClickListener(){
        binding.constraintCategory.setOnClickListener(this::onClick);
        binding.constraintDeclarations.setOnClickListener(this::onClick);
        binding.constraintEmployee.setOnClickListener(this::onClick);
        binding.constraintUser.setOnClickListener(this::onClick);
        binding.constraintOperations.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.constraint_user:
                Intent user = new Intent(getApplicationContext(),ShowResidentActivity.class);
                startActivity(user);
                break;
            case R.id.constraint_category:
                Intent category = new Intent(getApplicationContext(),ShowCategoryActivity.class);
                startActivity(category);
                break;
            case R.id.constraint_operations:
                Intent operations = new Intent(getApplicationContext(),ShowOperationsActivity.class);
                startActivity(operations);
                break;
            case R.id.constraint_declarations:
                Intent declarations = new Intent(getApplicationContext(),ShowAdvertisementsActivity.class);
                startActivity(declarations);
                break;
            case R.id.constraint_employee:
                Intent employee = new Intent(getApplicationContext(),ShowEmployeeActivity.class);
                startActivity(employee);
                break;

        }
    }

    private void logout(){
        AuthApiController.getInstance().logout(new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(HomeAdminActivity.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(HomeAdminActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}