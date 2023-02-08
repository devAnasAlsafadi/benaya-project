package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.AuthApiController;
import com.example.apifinalproject.databinding.ActivityChangePasswordBinding;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.google.android.material.snackbar.Snackbar;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListener();
    }

    private boolean checkPassword(){
        if (checkData()){
            String pass = binding.etNewPassword.getText().toString().trim();
            String pass_con = binding.etPasswordConfirmation.getText().toString().trim();
            if (pass.equals(pass_con)){
                return true;
            }else {
                Snackbar.make(binding.getRoot(),"Enter A true Password",Snackbar.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private boolean checkData(){
        if (!binding.etCurrentPassword.getText().toString().isEmpty()
                &&!binding.etNewPassword.getText().toString().isEmpty()
                &&!binding.etPasswordConfirmation.getText().toString().isEmpty()){
            return true;
        }
        Snackbar.make(binding.getRoot(),"Enter required data!",Snackbar.LENGTH_LONG).show();
        return false;
    }

    private void performChangePassword(){
        if (checkPassword()){
            changePassword();
        }

    }

    private void setOnClickListener(){
        binding.btnChangePassword.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_password:
                performChangePassword();
                break;
        }
    }

    private void changePassword(){
        String currentPassword = binding.etCurrentPassword.getText().toString().trim();
        String newPassword = binding.etNewPassword.getText().toString().trim();
        String passwordConfirmation = binding.etPasswordConfirmation.getText().toString().trim();
        AuthApiController.getInstance().changePassword(currentPassword, newPassword, passwordConfirmation, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}