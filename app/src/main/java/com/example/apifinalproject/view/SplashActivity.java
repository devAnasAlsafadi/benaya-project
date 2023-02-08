package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.apifinalproject.R;
import com.example.apifinalproject.databinding.ActivitySplashBinding;
import com.example.apifinalproject.interfaces.TypeCustomer;

public class SplashActivity extends AppCompatActivity  {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controllerSplash();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    /*FadeIn*/
    private void controllerSplash(){
        YoYo.with(Techniques.BounceInRight).repeat(0).duration(2000).playOn(binding.imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
            }
        },3000);
    }

}