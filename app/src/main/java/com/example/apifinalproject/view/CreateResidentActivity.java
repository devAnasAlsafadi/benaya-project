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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.apifinalproject.R;
import com.example.apifinalproject.api.controller.ResidentApiController;
import com.example.apifinalproject.databinding.ActivityCreateResidentBinding;
import com.example.apifinalproject.enums.KeysIntent;
import com.example.apifinalproject.interfaces.ProcessCallback;
import com.example.apifinalproject.interfaces.Types;
import com.example.apifinalproject.model.Resident;
import com.example.apifinalproject.pref.AppSharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CreateResidentActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityCreateResidentBinding binding;

    private ActivityResultLauncher<String> permissionLauncher;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private Bitmap bitmapImage;
    private Uri UriImage;
    private String gender;
    String statsPicked ;
   private InputStream iStream;
   private boolean statsPermission;
   boolean checkStats;
   String update ="Update";
   String create = "Create";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCreateResidentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkStats = AppSharedPreference.getInstance().sharedPreferences.getBoolean(Types.chickPermission,false);

        if (getIntentValue() == 1){
            binding.tvTitle.setText("Create Resident");
            binding.btnCreateResident.setText(create);
        }else if (getIntentValue() == 2){
            binding.tvTitle.setText("Update Resident");
            binding.btnCreateResident.setText(update);
        }



    }

    //معرفة العملية هل هي تعديل ام حذف
    private int getIntentValue() {
        return getIntent().getIntExtra(KeysIntent.intentResidentUpdatedCreated.name(), 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeView();
    }

    private void initializeView(){
        setUpResultLauncher();
        setOnClickListener();
        controlGenderSelection();
        pickImage();

    }

    private void setOnClickListener(){
        binding.btnCreateResident.setOnClickListener(this::onClick);
        binding.ivPick.setOnClickListener(this::onClick);
        binding.ivBackCreateResident.setOnClickListener(this::onClick);
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
                    binding.ivShow.setImageBitmap(result);
                }
            }
        });


        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result!=null) {
                    UriImage = result;
                    binding.ivShow.setImageURI(result);
                }
            }
        });
    }


    //التحكم بعمليات تحديد الجنس
    private void controlGenderSelection(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                gender = i == R.id.male ? "M":"F";
            }
        });
    }


    //تقوم بارجاع مصفوفة من نوع byte لصورة من الكاميرا
    private byte[] bitmapToByte(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    //تقوم بارجاع مصفوفة من نوع byte لصورة من الاستديو
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
            case R.id.btn_create_resident:
                String text = binding.btnCreateResident.getText().toString();
                if (text.equals(create)) {
                    createResident();
                }else {
                    update();
                }
                break;
            case R.id.iv_pick:
                showDialogOptionsImage();
                if (statsPermission){
                }
                break;
            case R.id.iv_back_createResident:
                onBackPressed();
                break;

        }
    }

    private void createResident(){
        ResidentApiController controller = new ResidentApiController();
        controller.createResident(getResident(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(CreateResidentActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onSuccess: "+message);
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(CreateResidentActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+message);
            }
        });
    }

    private void update(){
        Resident resident = getResident();
        resident.id = getIdResident();
        ResidentApiController residentApiController = new ResidentApiController();
        residentApiController.updateResident(resident, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Log.d("TAG", "onSuccess: "+message);
                Toast.makeText(CreateResidentActivity.this, message, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(String message) {
                Log.d("TAG", "onFailure: "+message);
                Toast.makeText(CreateResidentActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

    }



    private Resident getResident(){
        Resident resident = new Resident();
        if (statsPicked == null){
            Toast.makeText(this, "الرجاء ادخال صورة", Toast.LENGTH_SHORT).show();
        }else {
            if (statsPicked.equals(Types.camera)) {
                if (bitmapToByte() != null) {
                    resident.imageArrayByte = bitmapToByte();
                }
            } else {
                if (getBytes() != null) {
                    resident.imageArrayByte = getBytes();
                }
            }
        }
        resident.email = binding.etEmail.getText().toString();
        resident.familyMembers = binding.etFamilyMembers.getText().toString();
        resident.gender = gender;
        resident.name = binding.etName.getText().toString();
        resident.nationalNumber = binding.etNationalNumber.getText().toString();
        resident.mobile = binding.etMobile.getText().toString();
        return resident;
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
                            Toast.makeText(CreateResidentActivity.this, "Do not have  permission", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(CreateResidentActivity.this, "Do not have  permission", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private int getIdResident(){
        return getIntent().getIntExtra(KeysIntent.idUpdatedResident.name(), 0);
    }

}