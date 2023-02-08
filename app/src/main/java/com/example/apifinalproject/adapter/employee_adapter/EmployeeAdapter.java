package com.example.apifinalproject.adapter.employee_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.adapter.resident_adapter.ResidentHolder;
import com.example.apifinalproject.databinding.CustomeResidentBinding;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Employee;
import com.example.apifinalproject.model.Resident;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeHolder> {

    List<Employee> list;
    ActionTypeCallback actionTypeCallback;
    int pos;

    public EmployeeAdapter(List<Employee> list, ActionTypeCallback actionTypeCallback) {
        this.list = list;
        this.actionTypeCallback = actionTypeCallback;
    }

    public void setList(List<Employee> list) {
//        this.list = list;
        this.list.clear();
        this.list.addAll(list);
        notifyItemRangeInserted(0,list.size());
//        notifyDataSetChanged();
    }

    public void delete(int pos){
        this.list.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
//        notifyItemRangeInserted(0,this.list.size()-1);
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomeResidentBinding binding = CustomeResidentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new EmployeeHolder(binding,actionTypeCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        holder.setData(list.get(position),position);
        this.pos = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
