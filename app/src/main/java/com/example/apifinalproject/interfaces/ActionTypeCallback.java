package com.example.apifinalproject.interfaces;


import com.example.apifinalproject.enums.ActionType;

public interface ActionTypeCallback {
    void onActionDeleteUpdateCallback(ActionType actionType , int id, int position);
}