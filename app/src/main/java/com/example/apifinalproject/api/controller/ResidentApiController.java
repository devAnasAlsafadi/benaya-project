package com.example.apifinalproject.api.controller;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Model;
import com.example.apifinalproject.model.Resident;


public class ResidentApiController {


    public void createResident(Resident resident ,ProcessCallback callback){
        if (!resident.mobile.isEmpty()&&
               !resident.nationalNumber.isEmpty()
                 &&!resident.familyMembers.isEmpty()
                   &&!resident.email.isEmpty()
                     && !resident.gender.isEmpty()
                       &&resident.imageArrayByte!=null
                         &&!resident.name.isEmpty()){
            resident.createResident(callback);
        }else {
            callback.onFailure("Enter Required data!");
        }
    }

    public void getResident(ListCallback<Resident> callback){
        Resident.getResident(callback);
    }

    public void updateResident(Resident resident , ProcessCallback callback){
        if (!resident.mobile.isEmpty()&&
                !resident.nationalNumber.isEmpty()
                &&!resident.familyMembers.isEmpty()
                &&!resident.email.isEmpty()
                && !resident.gender.isEmpty()
                &&resident.imageArrayByte!=null
                &&!resident.name.isEmpty()
                &&!resident.method.isEmpty()){
            resident.updateResident(callback);
        }
        else{
            callback.onFailure("Enter Required data!");
        }
    }

    public void delete(int id , ProcessCallback callback){
        Resident resident = new Resident();
        resident.id = id;
        resident.deleteResident(callback);
    }
}
