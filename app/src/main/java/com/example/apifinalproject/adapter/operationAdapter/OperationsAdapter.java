package com.example.apifinalproject.adapter.operationAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.databinding.CustomeOperationsCategoryBinding;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Operation;

import java.util.List;

public class OperationsAdapter  extends RecyclerView.Adapter<OperationsHolder> {

    List<Operation> list;
    ActionTypeCallback callback;
    String process;

    public OperationsAdapter(List<Operation> list,String process) {
        this.list = list;
        this.process = process;
    }

    @NonNull
    @Override
    public OperationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomeOperationsCategoryBinding binding = CustomeOperationsCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OperationsHolder(binding,callback);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationsHolder holder, int position) {
                holder.setData(list.get(position),  position,process);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }





    public void setCallback(ActionTypeCallback callback) {
        this.callback = callback;
    }

    public void delete(int positions){
        this.list.remove(positions);
        notifyItemRemoved(positions);
        notifyDataSetChanged();

    }

    public void setList(List<Operation> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyItemRangeInserted(0,list.size());
    }




}
