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
import com.example.apifinalproject.adapter.resident_adapter.ResidentAdapter;
import com.example.apifinalproject.api.controller.ResidentApiController;
import com.example.apifinalproject.databinding.ActivityShowResidentBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.fragment.DeleteFragment;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.interfaces.DeleteFragmentListener;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Resident;

import java.util.ArrayList;
import java.util.List;

public class ShowResidentActivity extends AppCompatActivity implements ActionTypeCallback , View.OnClickListener, DeleteFragmentListener {
    ActivityShowResidentBinding binding;
    ResidentAdapter adapter;
    int idRes,positionRes;
    private DeleteFragment deleteFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowResidentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }

    @Override
    protected void onStart() {
        super.onStart();
        controllerAnimation();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        setOnClickListener();
        inilializeAdapter();
        getUser();
    }

    private void inilializeAdapter(){
        adapter = new ResidentAdapter(new ArrayList<>(),this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }



    private void getUser(){
        ResidentApiController controller = new ResidentApiController();
        controller.getResident(new ListCallback<Resident>() {
            @Override
            public void onSuccess(List<Resident> list) {
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
        this.idRes = id;
        this.positionRes = position;
        if (actionType == ActionType.delete){
            deleteFragment = DeleteFragment.newInstance();
            deleteFragment.show(getSupportFragmentManager(),null);
        }
        else if (actionType == ActionType.update){
            Intent intent = new Intent(getApplicationContext(),CreateResidentActivity.class);
            intent.putExtra(KeysIntent.intentResidentUpdatedCreated.name(),2);
            intent.putExtra(KeysIntent.idUpdatedResident.name(),id);
            startActivity(intent);
        }
    }

    private void setOnClickListener(){
        binding.fbAddRes.setOnClickListener(this::onClick);
        binding.ivBackResident.setOnClickListener(this::onClick);
    }

    private void controllerAnimation(){
        YoYo.with(Techniques.ZoomInUp).repeat(0).duration(1000).playOn(binding.fbAddRes);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.RotateIn).repeat(0).duration(1000).playOn(binding.fbAddRes);
            }
        },1000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fb_add_res:
                Intent intent = new Intent(getApplicationContext(),CreateResidentActivity.class);
                intent.putExtra(KeysIntent.intentResidentUpdatedCreated.name(),1);
                startActivity(intent);
                break;
            case R.id.iv_back_resident:
                onBackPressed();
                break;

        }
    }



    @Override
    public void deleteListener() {
        deleteResident();
    }


    private void deleteResident(){
        ResidentApiController residentApiController = new ResidentApiController();
        residentApiController.delete(idRes, new ProcessCallback() {
                    @Override
                    public void onSuccess(String message) {
                        deleteFragment.dismiss();
                        adapter.delete(positionRes);
                        Toast.makeText(ShowResidentActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(ShowResidentActivity.this, message, Toast.LENGTH_SHORT).show();

                    }
                });
    }

}