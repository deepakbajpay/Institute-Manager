package com.coderzpassion.studentfaculty.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.TimeTableItem;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.Arrays;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class TImeTableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 111;
    Spinner spinnerDepartment,spinnerBatch;
    ImageView ivAddTimetable,ivEdit;
    ArrayList<String> departments;
    ArrayList<String> batch;
    DatabaseHelper databaseHelper;
    PhotoView photoView;
    Boolean userTypeAdmin;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        databaseHelper= new DatabaseHelper(TImeTableActivity.this);

        userType =  SharedPrefs.getString(this,SharedPrefs.type);
        userTypeAdmin =userType.equalsIgnoreCase("admin");

        departments =new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        batch = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.batch)));

        spinnerBatch = (Spinner)findViewById(R.id.timetable_batch_spinner);
        spinnerDepartment = (Spinner)findViewById(R.id.timetable_department_spinner);
        ivAddTimetable = (ImageView)findViewById(R.id.timetable_add_iv);
        ivEdit = (ImageView)findViewById(R.id.timetable_edit_iv);
        photoView = (PhotoView)findViewById(R.id.preview_photo_holder);

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,departments);
        spinnerDepartment.setAdapter(departmentAdapter);
        ArrayAdapter<String> batchAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.batch));
        spinnerBatch.setAdapter(batchAdapter);
        spinnerBatch.setOnItemSelectedListener(this);
        spinnerDepartment.setOnItemSelectedListener(this);
        ivAddTimetable.setOnClickListener(this);
        ivEdit.setOnClickListener(this);

        if (Constants.USER_TYPE_FACULTY.equalsIgnoreCase(userType)||Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)){
            spinnerDepartment.setSelection(departments.indexOf(SharedPrefs.getString(this,SharedPrefs.department)));
        }
        if (Constants.USER_TYPE_STUDENT.equalsIgnoreCase(userType)){
            spinnerDepartment.setSelection(departments.indexOf(SharedPrefs.getString(this,SharedPrefs.department)));
            spinnerBatch.setSelection(batch.indexOf(SharedPrefs.getString(this,SharedPrefs.batch)));
        }

        String path = databaseHelper.getTimeTable((String)spinnerDepartment.getSelectedItem(),(String)spinnerBatch.getSelectedItem());
        if (path!=null) {
            setImage(path);

        }
        else
            showAddTimetableButton();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String path = databaseHelper.getTimeTable((String)spinnerDepartment.getSelectedItem(),(String)spinnerBatch.getSelectedItem());
        if (path!=null) {
            setImage(path);

        }
        else
            showAddTimetableButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (((String)spinnerDepartment.getSelectedItem()).equalsIgnoreCase("Select Department")){
            Toast.makeText(TImeTableActivity.this, "Please select department", Toast.LENGTH_SHORT).show();
            return;
        }else if (((String)spinnerBatch.getSelectedItem()).equalsIgnoreCase("Select Batch")){
            Toast.makeText(TImeTableActivity.this, "Please select batch", Toast.LENGTH_SHORT).show();
            return;
        }
        getStoragePermission(STORAGE_PERMISSION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && data != null) {
            ArrayList<String> pickecImage = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
            if (pickecImage != null) {
                if (pickecImage.size()>0) {
                    TimeTableItem item = new TimeTableItem();
                    item.setDepartment((String) spinnerDepartment.getSelectedItem());
                    item.setBatch((String) spinnerBatch.getSelectedItem());
                    item.setPath(pickecImage.get(0));
                    if (databaseHelper.addTimeTable(item) != -1) {
                        setImage(pickecImage.get(0));
                    } else
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "We can't proceed without Storage permission", Toast.LENGTH_SHORT).show();
                return;
            }
            if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "We can't proceed without Camera permission", Toast.LENGTH_SHORT).show();
                return;
            }
            else
                openGallery();
        }
    }

    public void getStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, requestCode);
                return;
            }
        }
       openGallery();
    }

    private void openGallery(){
        FilePickerBuilder.getInstance().setMaxCount(1)
                .enableCameraSupport(true)
                .pickPhoto(TImeTableActivity.this);
    }

    private void setImage(String path){
        photoView.setVisibility(View.VISIBLE);
        photoView.setImageBitmap(BitmapFactory.decodeFile(path));
        ivAddTimetable.setVisibility(View.GONE);
        if (userTypeAdmin)
        ivEdit.setVisibility(View.VISIBLE);
    }

    private void showAddTimetableButton(){
        if (userTypeAdmin) {
            ivEdit.setVisibility(View.GONE);
            ivAddTimetable.setVisibility(View.VISIBLE);
        }
        photoView.setVisibility(View.GONE);
    }
}
