package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.AuthApiController;
import com.example.apifinalproject.databinding.ActivityLoginBinding;
import com.example.apifinalproject.fragment.ForgetPasswordFragment;
import com.example.apifinalproject.interfaces.ForgetPasswordListener;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.TypeCustomer;
import com.example.apifinalproject.pref.AppSharedPreference;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ForgetPasswordListener {
    ActivityLoginBinding binding;
    int counter=0;
    ForgetPasswordFragment passwordFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListener();
        binding.progressBar.setVisibility(View.GONE);



    }

    @Override
    protected void onStart() {
        super.onStart();
        controllerLogin();
    }

    private void setOnClickListener(){
        binding.btnSignIn.setOnClickListener(this::onClick);
        binding.tvForgetPassword.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:
                performLogin();
                break;
            case R.id.tv_forget_password:
                 passwordFragment = ForgetPasswordFragment.newInstance();
                passwordFragment.show(getSupportFragmentManager(),null);
                break;
        }
    }

    private void performLogin(){
        if (checkData()){
            login();
        }else {
            Snackbar.make(binding.getRoot(),"Enter Required data!",Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean checkData(){
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()){
            return true;
        }
        return false;
    }

    private void login(){
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter++;
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.progressBar.setProgress(counter);
                    }
                });
            }
        };
        timer.schedule(timerTask,100,100);

        AuthApiController.getInstance().login(email, password, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                timer.cancel();
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                String type = AppSharedPreference.getInstance().getActorType();
                if (type.equals(TypeCustomer.login_admin)){
                    Intent intent = new Intent(getApplicationContext(),HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "not login as user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String message) {
                timer.cancel();
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void controllerLogin(){
        boolean stats = AppSharedPreference.getInstance().isLoggedIn();
        String typeCustomer = AppSharedPreference.getInstance().getActorType();
        if (stats && typeCustomer.equals(TypeCustomer.login_admin)){
            Intent intent = new Intent(getApplicationContext(),HomeAdminActivity.class);
            startActivity(intent);
            finish();
        }else if (stats && typeCustomer.equals(TypeCustomer.login_user)){

        }else
        {

        }
    }

    private void forgetPassword(String email){
        AuthApiController.getInstance().forgetPassword(email, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                passwordFragment.dismiss();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                passwordFragment.dismiss();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onClickRest(String email) {
        forgetPassword(email);
    }
}