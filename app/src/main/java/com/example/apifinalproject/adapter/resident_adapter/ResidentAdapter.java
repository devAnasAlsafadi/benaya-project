package com.example.apifinalproject.adapter.resident_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apifinalproject.databinding.CustomeResidentBinding;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Resident;

import java.util.List;


public class ResidentAdapter extends RecyclerView.Adapter<ResidentHolder> {

    List<Resident> list;
    ActionTypeCallback actionTypeCallback;

    public ResidentAdapter(List<Resident> list, ActionTypeCallback actionTypeCallback) {
        this.list = list;
        this.actionTypeCallback = actionTypeCallback;
    }


    public void setList(List<Resident> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyItemRangeInserted(0,list.size());
    }


    public void delete(int positions){
        this.list.remove(positions);
        notifyItemRemoved(positions);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public ResidentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomeResidentBinding binding = CustomeResidentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ResidentHolder(binding,actionTypeCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ResidentHolder holder, int position) {
        holder.setData(list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
