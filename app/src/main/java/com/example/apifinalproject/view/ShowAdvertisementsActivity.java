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
import com.example.apifinalproject.adapter.Advertisement_Adapter.AdvertisementAdapter;
import com.example.apifinalproject.api.controller.AdvertisementsApiController;
import com.example.apifinalproject.databinding.ActivityShowAdvertisementsBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.fragment.DeleteFragment;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.interfaces.DeleteFragmentListener;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Advertisements;

import java.util.ArrayList;
import java.util.List;

public class ShowAdvertisementsActivity extends AppCompatActivity implements View.OnClickListener, ActionTypeCallback, DeleteFragmentListener {
    ActivityShowAdvertisementsBinding binding;
    AdvertisementAdapter adapter ;
    private DeleteFragment deleteFragment;
    private  int id ,positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAdvertisementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


    private void controllerAnimation(){
        YoYo.with(Techniques.ZoomInUp).repeat(0).duration(1000).playOn(binding.fbAddAdvertisement);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.RotateIn).repeat(0).duration(1000).playOn(binding.fbAddAdvertisement);
            }
        },1000);
    }





    private void getAdvertisements(){
        AdvertisementsApiController.getInstance().getAdvertisements(new ListCallback<Advertisements>() {
            @Override
            public void onSuccess(List<Advertisements> list) {
                if (list.size() == 0){
                    binding.tvFound.setVisibility(View.VISIBLE);
                }
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.rv.setVisibility(View.VISIBLE);
                adapter.setList(list);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ShowAdvertisementsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        controllerAnimation();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        initializeView();
        getAdvertisements();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initializeView(){
        initializeAdapter();
        setOnClickListener();
    }

    private void initializeAdapter(){
        adapter = new AdvertisementAdapter(new ArrayList<>(),this::onActionDeleteUpdateCallback);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }







    @Override
    public void onActionDeleteUpdateCallback(ActionType actionType, int id, int position) {
        this.id = id;
        this.positions = position;
        if (actionType.equals(ActionType.delete)){
            deleteFragment =DeleteFragment.newInstance();
            deleteFragment.show(getSupportFragmentManager(),null);

        } else if (actionType == ActionType.update){
            Intent intent = new Intent(getApplicationContext(),CreateAdvertisementsActivity.class);
            intent.putExtra(KeysIntent.intentAdvertisementsUpdatedCreated.name(),2);
            intent.putExtra(KeysIntent.idAdvertisementsIntent.name(), id);
            startActivity(intent);
        }
    }

    @Override
    public void deleteListener() {
        deleteAdvertisements();
    }

    private void deleteAdvertisements(){

        AdvertisementsApiController.getInstance().deleteAdvertisements(id, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(ShowAdvertisementsActivity.this, message, Toast.LENGTH_SHORT).show();
                deleteFragment.dismiss();
                adapter.delete(positions);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ShowAdvertisementsActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setOnClickListener(){
        binding.fbAddAdvertisement.setOnClickListener(this::onClick);
        binding.ivBackAdvertisements.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fb_add_advertisement:
                Intent intent = new Intent(getApplicationContext(),CreateAdvertisementsActivity.class);
                intent.putExtra(KeysIntent.intentAdvertisementsUpdatedCreated.name(),1);
                startActivity(intent);
                break;
            case R.id.iv_back_Advertisements:
                onBackPressed();
                break;
        }
    }
}