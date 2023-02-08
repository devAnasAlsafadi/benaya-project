package com.example.apifinalproject.adapter.operationAdapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.R;
import com.example.apifinalproject.databinding.CustomeOperationsCategoryBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.interfaces.Types;
import com.example.apifinalproject.model.Operation;

public class OperationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CustomeOperationsCategoryBinding binding;
    ActionTypeCallback callback;
    private int id,position;

    public OperationsHolder(CustomeOperationsCategoryBinding binding,ActionTypeCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        this.callback  = callback;
        setOnClickListener();
    }



    public void setData(Operation operation , int position ,String process){

        this.id = operation.id;
        this.position = position;

        if (process.equals(Types.categoryProcess)) {
            binding.btnDeleteOperations.setVisibility(View.GONE);
            binding.btnUpdateOperations.setVisibility(View.GONE);
            binding.layout7.setVisibility(View.GONE);
        }

        binding.tvCategoryName.setText(operation.categoryName);
        binding.tvDate.setText(operation.date);
        binding.tvAmount.setText(operation.amount);
        binding.tvDetails.setText(operation.details);

        if (operation.resident != null) {
            binding.tvMobile.setText(operation.resident.mobile);
            binding.tvActorName.setText(operation.resident.name);
            if (process.equals(Types.categoryProcess)) {
            binding.btnDeleteOperations.setVisibility(View.GONE);
            binding.btnUpdateOperations.setVisibility(View.GONE);
            binding.layout7.setVisibility(View.GONE);

            } else if (process.equals(Types.operationProcess)) {
                if (operation.resident.familyMembers != null) {
                    binding.tvActorType.setText("Resident");
                } else {
                    binding.tvActorType.setText("Employee");
                }
            }

        }else {
            binding.layout5.setVisibility(View.GONE);
            binding.layout6.setVisibility(View.GONE);
            binding.layout7.setVisibility(View.GONE);
        }



        }




    private void setOnClickListener(){
        binding.btnDeleteOperations.setOnClickListener(this::onClick);
        binding.btnUpdateOperations.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete_operations:
                if (callback != null) {
                    callback.onActionDeleteUpdateCallback(ActionType.delete, id, position);
                }
                break;
            case R.id.btn_update_operations:
                if (callback != null) {
                    callback.onActionDeleteUpdateCallback(ActionType.update, id, position);
                }
                break;
        }
    }
}
