package com.example.apifinalproject.adapter.Advertisement_Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.R;
import com.example.apifinalproject.databinding.CustomeAdvertisementsBinding;
import com.example.apifinalproject.databinding.CustomeResidentBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Advertisements;
import com.example.apifinalproject.model.Employee;
import com.squareup.picasso.Picasso;

public class AdvertisementHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    CustomeAdvertisementsBinding binding;
    ActionTypeCallback callback;
    int positionHolder;
    int id;


    public AdvertisementHolder(CustomeAdvertisementsBinding binding,ActionTypeCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        this.callback = callback;
        setOnClickListener();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_delete_adv:
                if (callback != null) {
                    callback.onActionDeleteUpdateCallback(ActionType.delete, id, positionHolder);
                }
                break;
            case R.id.btn_update_adv:
                if (callback != null) {
                    callback.onActionDeleteUpdateCallback(ActionType.update, id, positionHolder);
                }
                break;
        }
    }

    public void setData(Advertisements advertisements , int position) {
        this.positionHolder = position;
        this.id = advertisements.id;
        if (advertisements.imageUrl!=null){
            Picasso.get().load(advertisements.imageUrl).into(binding.imageView);
        }else {
            binding.imageView.setVisibility(View.GONE);
        }
        binding.tvTitle.setText(advertisements.title);
        binding.tvInfo.setText(advertisements.info);

    }


    private void setOnClickListener(){
        binding.btnDeleteAdv.setOnClickListener(this::onClick);
        binding.btnUpdateAdv.setOnClickListener(this::onClick);
    }




}
