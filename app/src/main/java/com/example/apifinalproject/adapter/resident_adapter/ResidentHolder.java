package com.example.apifinalproject.adapter.resident_adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.R;
import com.example.apifinalproject.databinding.CustomeResidentBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Resident;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import retrofit2.http.Url;

public class ResidentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CustomeResidentBinding binding;
    ActionTypeCallback callback;
    int positionHolder;
    int id;


    public ResidentHolder(CustomeResidentBinding binding , ActionTypeCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        this.callback = callback;
        setOnClickListener();
    }

    public void setData(Resident resident , int position){
        this.positionHolder = position;
        this.id = resident.id;


        Picasso.get().load(resident.imageUrl).into(binding.imageView);

        binding.tvName.setText(resident.name);
        binding.tvNumber.setText(resident.mobile);
        binding.tvFamily.setText(resident.familyMembers);
    }

    private void setOnClickListener(){
        binding.btnDeleteRes.setOnClickListener(this::onClick);
        binding.btnUpdateRes.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete_res:
                if (callback != null) {
                    callback.onActionDeleteUpdateCallback(ActionType.delete, id, positionHolder);
                }
                break;
            case R.id.btn_update_res:
                if (callback != null) {
                    callback.onActionDeleteUpdateCallback(ActionType.update, id, positionHolder);
                }
                break;
        }
    }
}
