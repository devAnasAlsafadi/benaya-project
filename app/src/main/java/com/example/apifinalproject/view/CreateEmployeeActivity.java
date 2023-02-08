package com.example.apifinalproject.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.EmployeeApiController;
import com.example.apifinalproject.databinding.ActivityCreateEmployeeBinding;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.Types;
import com.example.apifinalproject.model.Employee;
import com.example.apifinalproject.pref.AppSharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CreateEmployeeActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityCreateEmployeeBinding binding ;

    private ActivityResultLauncher<String> permissionLauncher;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private Bitmap bitmapImage;
    private Uri UriImage;
    String statsPicked ;
    private InputStream iStream;
    private boolean statsPermission;
    String update ="Update";
    String create = "Create";
    private boolean checkStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCreateEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkStats =AppSharedPreference.getInstance().sharedPreferences.getBoolean(Types.chickPermission,false);


        if (getIntentValue() == 1){
            binding.tvTitle.setText("Create Employee");
            binding.btnCreateEmployee.setText(create);
        }else if (getIntentValue() == 2){
            binding.tvTitle.setText("Update Employee");
            binding.btnCreateEmployee.setText(update);
        }


    }






    private int getIntentValue() {
        return getIntent().getIntExtra(KeysIntent.intentEmployeeUpdatedCreated.name(), 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeView();
    }

    private void initializeView(){
        setUpResultLauncher();
        setOnClickListener();
        pickImage();

    }

    private void setOnClickListener(){
        binding.btnCreateEmployee.setOnClickListener(this::onClick);
        binding.ivPickImageEmployee.setOnClickListener(this::onClick);
        binding.ivBackEmployeeCreate.setOnClickListener(this::onClick);
    }

    private void setUpResultLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    AppSharedPreference.getInstance().editor.putBoolean(Types.chickPermission,true).apply();
                }else {

                }

            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    bitmapImage = result;
                    binding.ivShowEmployee.setImageBitmap(result);
                }
            }
        });


        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result!=null) {
                    UriImage = result;
                    binding.ivShowEmployee.setImageURI(result);
                }
            }
        });
    }



    private byte[] bitmapToByte(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }


    public byte[] getBytes()  {
        if (UriImage !=null) {
            try {
                iStream = getContentResolver().openInputStream(UriImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while (true) {
                try {
                    if (!((len = iStream.read(buffer)) != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }
        return null;
    }


    private void pickImage() {
        if (checkStats) {

        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_employee:
                String text = binding.btnCreateEmployee.getText().toString();
                if (text.equals(create)) {
                    createEmployee();
                }else {
                    update();
                }
                break;
            case R.id.iv_pick_image_employee:
                showDialogOptionsImage();
                break;
            case R.id.iv_back_employee_create:
                onBackPressed();
                break;

        }
    }

    private void createEmployee(){
        EmployeeApiController controller = new EmployeeApiController();
        controller.createEmployee(getEmployee(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(CreateEmployeeActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onSuccess: "+message);
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(CreateEmployeeActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+message);
            }
        });

    }

    private void update(){
        Employee employee = getEmployee();
        employee.id = getIdResident();
        EmployeeApiController controller = new EmployeeApiController();
        controller.updateEmployee(employee, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Log.d("TAG", "onSuccess: "+message);
                Toast.makeText(CreateEmployeeActivity.this, message, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(CreateEmployeeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }



    private Employee getEmployee(){
        Employee employee = new Employee();
        if (statsPicked == null){
            Toast.makeText(this, "الرجاء ادخال صورة", Toast.LENGTH_SHORT).show();
        }else {
            if (statsPicked.equals(Types.camera)) {
                bitmapToByte();
                employee.imageArrayByte = bitmapToByte();
            } else {
                if (getBytes() != null) {
                    employee.imageArrayByte = getBytes();
                }
            }
        }
        employee.name = binding.etName.getText().toString();
        employee.nationalNumber = binding.etNationalNumber.getText().toString();
        employee.mobile = binding.etMobile.getText().toString();
        return employee;
    }
    private void showDialogOptionsImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select image")
                .setMessage("select image gallery or camera")
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        statsPicked = Types.gallery;
                        if (checkStats){
                            galleryLauncher.launch("image/*");
                        }else{
                            Toast.makeText(CreateEmployeeActivity.this, "Do not have  permission", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        statsPicked =Types.camera;
                        if (checkStats){
                            cameraLauncher.launch(null);
                        }else{
                            Toast.makeText(CreateEmployeeActivity.this, "Do not have  permission", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private int getIdResident(){
        return getIntent().getIntExtra(KeysIntent.idUpdatedEmployee.name(), 0);
    }
}