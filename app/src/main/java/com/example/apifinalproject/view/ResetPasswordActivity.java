package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.AuthApiController;
import com.example.apifinalproject.databinding.ActivityResetPasswordBinding;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }




    @Override
    protected void onStart() {
        super.onStart();
        initializeView();
    }


    private void initializeView(){
        setOnClickListener();
    }

    private void setOnClickListener(){
        binding.btnResetPass.setOnClickListener(this::onClick);
    }

    private void performResetPassword(){
        if (checkPassword()){
            resetPassword();
        }

    }

    private boolean checkPassword(){
        if (checkData()){
            String pass = binding.etPassword.getText().toString().trim();
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
        if (!binding.etEmail.getText().toString().isEmpty()
              &&!binding.etCode.getText().toString().isEmpty()
               &&!binding.etPassword.getText().toString().isEmpty()
                &&!binding.etPasswordConfirmation.getText().toString().isEmpty()){
            return true;
        }
        Snackbar.make(binding.getRoot(),"Enter required data!",Snackbar.LENGTH_LONG).show();
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reset_pass:
                performResetPassword();
                break;
        }
    }

    private void resetPassword(){
        String email = binding.etEmail.getText().toString().trim();
        String code = binding.etCode.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String passwordConfirmation = binding.etPasswordConfirmation.getText().toString().trim();
        AuthApiController.getInstance().resetPassword(email, code, password, passwordConfirmation, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}