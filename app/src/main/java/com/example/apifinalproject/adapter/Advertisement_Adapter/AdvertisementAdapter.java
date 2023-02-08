package com.example.apifinalproject.adapter.Advertisement_Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.adapter.employee_adapter.EmployeeHolder;
import com.example.apifinalproject.databinding.CustomeAdvertisementsBinding;
import com.example.apifinalproject.databinding.CustomeResidentBinding;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Advertisements;
import com.example.apifinalproject.model.Employee;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementHolder> {
    List<Advertisements> list;
    ActionTypeCallback actionTypeCallback;


    public AdvertisementAdapter(List<Advertisements> list, ActionTypeCallback actionTypeCallback) {
        this.list = list;
        this.actionTypeCallback = actionTypeCallback;
    }


        public void setList(List<Advertisements> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void delete(int pos){
        this.list.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdvertisementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomeAdvertisementsBinding binding = CustomeAdvertisementsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdvertisementHolder(binding,actionTypeCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementHolder holder, int position) {
        holder.setData(list.get(position),holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
