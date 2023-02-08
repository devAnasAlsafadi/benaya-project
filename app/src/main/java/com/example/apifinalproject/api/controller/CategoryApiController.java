package com.example.apifinalproject.api.controller;

import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.model.Category;
import com.example.apifinalproject.model.Operation;

public class CategoryApiController {

    public void getCategory(ListCallback<Category> callback){
        Category.getCategory(callback);
    }

//    public void getOperationsByCategoryId(String id , ListCallback<Operation> callback){
//        if (!id.isEmpty()) {
//            Operation operation = new Operation();
//            operation.categoryId = id;
//            operation.getOperationsByCategoryId(callback);
//        }else
//        {
//            callback.onFailure("please Enter id!");
//        }
//
//    }


    public void getOperationsByCategoryId(String id , ListCallback<Operation> callback){
        if (!id.isEmpty()) {
            Category category = new Category();
            category.id = Integer.valueOf(id);
            category.getOperationsByCategoryId(callback);
        }else
        {
            callback.onFailure("please Enter id!");
        }

    }
}

