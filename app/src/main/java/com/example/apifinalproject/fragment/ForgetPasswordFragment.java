package com.example.apifinalproject.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.apifinalproject.api.controller.AuthApiController;
import com.example.apifinalproject.databinding.FragmentForgetPasswordBinding;
import com.example.apifinalproject.interfaces.ForgetPasswordListener;
import com.example.apifinalproject.interfaces.ProcessCallback;


public class ForgetPasswordFragment extends DialogFragment {

    ForgetPasswordListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForgetPasswordListener){
            listener = (ForgetPasswordListener) context;
        }else {
            throw new ClassCastException("not implement for ForgetPasswordListener ");
        }
    }

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentForgetPasswordBinding binding = FragmentForgetPasswordBinding.inflate(getLayoutInflater());
        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Enter required data!");
                }else {
                    listener.onClickRest(binding.etEmail.getText().toString().trim());
                }
            }
        });
        builder.setView(binding.getRoot());
        return builder.create();
    }

}