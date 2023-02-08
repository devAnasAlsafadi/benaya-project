package com.example.apifinalproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.CategoryApiController;
import com.example.apifinalproject.api.controller.EmployeeApiController;
import com.example.apifinalproject.api.controller.OperationsApiController;
import com.example.apifinalproject.api.controller.ResidentApiController;
import com.example.apifinalproject.databinding.ActivityCreateOperationsBinding;
import com.example.apifinalproject.enums.CategoryTypes;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.interfaces.ListCallback;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.model.Category;
import com.example.apifinalproject.model.Employee;
import com.example.apifinalproject.model.Operation;
import com.example.apifinalproject.model.Resident;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOperationsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityCreateOperationsBinding binding;
    private String[] actorType;
    private Calendar calendar;
    private final String update ="Update";
    private final String create = "Create";
    String idActor;
    String selectActorId , selectTypeActor , selectCategoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        controllerButtonName();

        actorType = getResources().getStringArray(R.array.Type_actor);
        ArrayList<String> strings = new ArrayList<>();
        ArrayAdapter<String> adapterCategoryId = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,strings);
        binding.spCategory.setAdapter(adapterCategoryId);
        ArrayAdapter<String> adapterActorType = new ArrayAdapter<>(this,R.layout.drop_down_item,actorType);
        binding.spCustomer.setAdapter(adapterActorType);


        binding.spCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectTypeActor= parent.getItemAtPosition(position).toString();
                if (selectTypeActor.equals("Employee")){
                    getEmployeeId();
                }else if (selectTypeActor.equals("Resident")){
                    getResidentId();
                }
            }
        });
        binding.spCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                if (s.equals(CategoryTypes.purchases.value)){
                    selectCategoryId = String.valueOf(3);
                    binding.spinnerCustomerType.setVisibility(View.GONE);
                    binding.spinnerNumberCustomer.setVisibility(View.GONE);
                }else if (s.equals(CategoryTypes.maintenance.value)){
                    selectCategoryId = String.valueOf(4);
                    binding.spinnerCustomerType.setVisibility(View.GONE);
                    binding.spinnerNumberCustomer.setVisibility(View.GONE);
                }else if (s.equals(CategoryTypes.populationServices.value)){
                    selectCategoryId = String.valueOf(1);
                    binding.spinnerCustomerType.setVisibility(View.VISIBLE);
                    binding.spinnerNumberCustomer.setVisibility(View.VISIBLE);
                }else if (s.equals(CategoryTypes.salaries.value)){
                    selectCategoryId = String.valueOf(2);
                    binding.spinnerCustomerType.setVisibility(View.VISIBLE);
                    binding.spinnerNumberCustomer.setVisibility(View.VISIBLE);
                }
            }
        });


        binding.spNumberCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectActorId = parent.getItemAtPosition(position).toString();
                if (selectTypeActor.equals("Employee")){
                    getNameEmployee();
                }else if (selectTypeActor.equals("Resident")){
                    getNameResident();
                }

            }
        });

    }



    //لجلب اسماء المستخدمين وعرضهم للعمليات
    private void getNameResident(){
        Resident.getResident(new ListCallback<Resident>() {
            @Override
            public void onSuccess(List<Resident> list) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).name.equals(selectActorId)){
                        idActor = String.valueOf(list.get(i).id);
                    }
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    //لجلب اسماء الموظفين وعرضهم للعمليات
    private void getNameEmployee(){
        EmployeeApiController.getEmployees(new ListCallback<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).name.equals(selectActorId)){
                        idActor = String.valueOf(list.get(i).id);
                    }
                }
            }
            @Override
            public void onFailure(String message) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        getCategoryId();
        setOnClickListener();


    }

    private void getCategoryId(){
        CategoryApiController controller = new CategoryApiController();
        controller.getCategory(new ListCallback<Category>() {
            @Override
            public void onSuccess(List<Category> list) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                  String val =list.get(i).name;
                  arrayList.add(val);
                }
                ArrayAdapter<String> adapterCategoryId = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,arrayList);
                binding.spCategory.setAdapter(adapterCategoryId);
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }
    private void getEmployeeId(){
        EmployeeApiController.getEmployees(new ListCallback<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                ArrayList<String> employee = new ArrayList<>();
                for (int i = 0; i <list.size(); i++) {
                    String val = list.get(i).name;
                    employee.add(val);
                }
                ArrayAdapter<String> adapterEmployeeId = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,employee);
                binding.spNumberCustomer.setAdapter(adapterEmployeeId);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
    private void getResidentId(){
        ResidentApiController controller = new ResidentApiController();
        controller.getResident(new ListCallback<Resident>() {
            @Override
            public void onSuccess(List<Resident> list) {
                ArrayList<String> resident = new ArrayList<>();
                for (int i = 0; i <list.size(); i++) {
                    String val = list.get(i).name;
                    resident.add(val);
                }
                ArrayAdapter<String> adapterResidentId = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,resident);
                binding.spNumberCustomer.setAdapter(adapterResidentId);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    private void setOnClickListener(){
        binding.btnCreateOperations.setOnClickListener(this::onClick);
        binding.ivDate.setOnClickListener(this::onClick);
        binding.ivBack.setOnClickListener(this::onClick);
    }

    private void performSaveOperations(){
        if (checkStatsActor()){
            createOperation();
        }
    }

    private void createOperation() {
        OperationsApiController.getInstance().saveOperations(getOperation(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Log.d("TAG", "onSuccess: "+message);
                Toast.makeText(CreateOperationsActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(CreateOperationsActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+message);
            }
        });
    }


    private void getDatePicker(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog =DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
               binding.etDate.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
               calendar = Calendar.getInstance();
               calendar.set(Calendar.YEAR,year);
               calendar.set(Calendar.MONTH,monthOfYear);
               calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            }
        },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
                );
        dialog.show(getSupportFragmentManager(),"");
    }

    private boolean checkStatsActor(){
        Log.d("TAG", "checkStatsActor: "+selectCategoryId);
        Log.d("TAG", "checkStatsActor: "+selectTypeActor);
        if (selectCategoryId!=null) {
            if (selectTypeActor != null) {
                if (selectCategoryId.equals(String.valueOf(1)) && selectTypeActor.equals("Resident")) {
                    return true;
                } else if (selectCategoryId.equals(String.valueOf(1)) && selectTypeActor.equals("Employee")) {
                    Snackbar.make(binding.getRoot(), "لا يمكنك مصادقة هذه العملية", Snackbar.LENGTH_LONG).show();
                    return false;
                } else if (selectCategoryId.equals(String.valueOf(2)) && selectTypeActor.equals("Employee")) {
                    return true;
                } else if (selectCategoryId.equals(String.valueOf(2)) && selectTypeActor.equals("Resident")) {
                    Snackbar.make(binding.getRoot(), "لا يمكنك مصادقة هذه العملية", Snackbar.LENGTH_LONG).show();
                    return false;
                }
            }
            if (selectCategoryId.equals(String.valueOf(3)) || selectCategoryId.equals(String.valueOf(4))) {
                selectTypeActor = null;
                selectActorId = null;
                return true;
            }
        }
        Snackbar.make(binding.getRoot(), "يرجى ادخال البيانات", Snackbar.LENGTH_LONG).show();
        return false;

    }

    private int getIdOperations(){
        return getIntent().getIntExtra(KeysIntent.idOperationsIntent.name(),0);
    }

    private Operation getOperation(){
        Operation operation = new Operation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
        if (calendar!=null) {
            String current = dateFormat.format(calendar.getTime());
            operation.date = current;
        }
        operation.details = binding.etDetails.getText().toString();
        operation.categoryId = selectCategoryId;
        operation.actorId = idActor;
        operation.amount = binding.etAmount.getText().toString();
        operation.object = selectTypeActor;

        return operation;
    }

    private void updateOperations(){
        Operation operation = getOperation();
        operation.id = getIdOperations();
        OperationsApiController.getInstance().updateOperations(operation, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(CreateOperationsActivity.this, message, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(CreateOperationsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void perFormUpdateOperations(){
        if (checkStatsActor()){
            updateOperations();
        }
    }

    private int getIntentValue(){
        return getIntent().getIntExtra(KeysIntent.intentOperationsUpdatedCreated.name(), 0);
    }


    private void controllerButtonName(){

        if (getIntentValue() == 1){
            binding.tvTitle.setText("Create Employee");
            binding.btnCreateOperations.setText(create);
        }else if (getIntentValue() == 2){
            binding.tvTitle.setText("Update Employee");
            binding.btnCreateOperations.setText(update);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_operations:
                String text = binding.btnCreateOperations.getText().toString();
                if (text.equals(create)) {
                    performSaveOperations();
                }else if (text.equals(update)){
                    perFormUpdateOperations();
                }
            break;
            case R.id.iv_date:
                getDatePicker();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}