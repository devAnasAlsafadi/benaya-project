package com.example.apifinalproject.api.controller;

import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Employee;

public class EmployeeApiController {



    public void createEmployee(Employee employee , ProcessCallback callback){
        if (!employee.mobile.isEmpty()&&
                !employee.nationalNumber.isEmpty()
                &&employee.imageArrayByte!=null
                &&!employee.name.isEmpty()){
            employee.createEmployee(callback);
        }else{
            callback.onFailure("Enter required data!");
        }
    }

    public void deleteEmployee(int id , ProcessCallback callback){
        Employee employee = new Employee();
        employee.id = id;

        employee.deleteEmployee(callback);
    }

    public void updateEmployee(Employee employee , ProcessCallback callback){
        if (!employee.mobile.isEmpty()&&
                !employee.nationalNumber.isEmpty()
                &&employee.imageArrayByte!=null
                &&!employee.name.isEmpty()){
            employee.updateEmployee(callback);
        }else
        {
            callback.onFailure("Enter required data!");
        }
    }

    public static void getEmployees(ListCallback<Employee> callback){
        Employee.getEmployees(callback);
    }
}
