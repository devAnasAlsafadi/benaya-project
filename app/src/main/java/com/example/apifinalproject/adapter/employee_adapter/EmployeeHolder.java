package com.example.apifinalproject.adapter.employee_adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apifinalproject.R;
import com.example.apifinalproject.databinding.CustomeResidentBinding;
import com.example.apifinalproject.enums.ActionType;
import com.example.apifinalproject.interfaces.ActionTypeCallback;
import com.example.apifinalproject.model.Employee;
import com.example.apifinalproject.model.Resident;
import com.squareup.picasso.Picasso;

public class EmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CustomeResidentBinding binding;
    ActionTypeCallback callback;
    int positionHolder;
    int id;


    public EmployeeHolder(CustomeResidentBinding binding , ActionTypeCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        this.callback = callback;
        setOnClickListener();
    }

    public void setData(Employee employee , int position){
        this.positionHolder = position;
        this.id = employee.id;


        Picasso.get().load(employee.imageUrl).into(binding.imageView);

        binding.tvName.setText(employee.name);
        binding.tvNumber.setText(employee.mobile);
        binding.tvFamily.setText(employee.nationalNumber);
        binding.tvHintFamily.setText("Nat Num is : ");
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
