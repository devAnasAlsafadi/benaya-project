package com.example.apifinalproject.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.apifinalproject.databinding.FragmentDeleteBinding;
import com.example.apifinalproject.interfaces.DeleteFragmentListener;


public class DeleteFragment extends DialogFragment {

    DeleteFragmentListener listener;

    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DeleteFragmentListener ){
            listener = (DeleteFragmentListener) context;
        }else {
            throw  new ClassCastException("do not implement for DeleteFragmentListener");
        }
    }

    public static DeleteFragment newInstance() {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentDeleteBinding binding = FragmentDeleteBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteListener();
            }
        });

        return builder.create();

    }
}