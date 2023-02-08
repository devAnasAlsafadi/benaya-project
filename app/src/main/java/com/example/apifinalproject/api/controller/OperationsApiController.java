package com.example.apifinalproject.api.controller;

import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Operation;


public class OperationsApiController {

    private static OperationsApiController instance;
    private OperationsApiController(){
    }

    public static OperationsApiController getInstance() {
        if (instance == null){
            instance = new OperationsApiController();
        }
        return instance;
    }

    public  void getOperations(ListCallback<Operation> callback){
        Operation.getOperations(callback);
    }

    public void saveOperations( Operation operation ,ProcessCallback callback){
        if (operation!=null){
            operation.saveOperations(callback);
        }else {
            callback.onFailure("Enter required data!");
        }

    }

    public void deleteOperations(int id , ProcessCallback callback){
        Operation operation = new Operation();
        operation.id = id;
        operation.deleteOperations(callback);
    }

    public void updateOperations(Operation operation ,ProcessCallback callback) {
        if (operation!=null){
            operation.saveOperations(callback);
        }else {
            callback.onFailure("Enter required data!");
        }
    }
}
