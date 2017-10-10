package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.model.Student;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

/**
 * Created by coderzpassion on 15/05/17.
 */

public class ActivityProfile extends BaseActivity {


    EditText et_fname, et_email, et_password, et_confirm_password, et_roll,et_contact;
    Spinner spinner_department, spinner_batch;
    Button btn_register, btn_home;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    Student student;
    Faculty faculty;
    String[] departments = {"Computer Science", "Textile", "Civil", "Mechanical", "Electrical", "Electronics and Communication"};
    String[] batch = {"2k14", "2k15", "2k16", "2k17"};
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Profile</font>"));
        userType = getIntent().getExtras().getString(Constants.USER_TYPE);

        databaseHelper = new DatabaseHelper(this);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_roll = (EditText) findViewById(R.id.et_roll);
        et_email = (EditText) findViewById(R.id.et_email);
        et_contact = (EditText)findViewById(R.id.et_contact);
        et_email.setEnabled(false);

        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        spinner_department = (Spinner) findViewById(R.id.spinner_department);
        spinner_batch = (Spinner) findViewById(R.id.spinner_batch);

        if(userType.equalsIgnoreCase(Constants.USER_TYPE_FACULTY)){
            spinner_batch.setVisibility(View.GONE);
            et_roll.setVisibility(View.GONE);
        }
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, departments);
        spinner_department.setAdapter(departmentAdapter);
        ArrayAdapter<String> batchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, batch);
        spinner_batch.setAdapter(batchAdapter);

        btn_register = (Button) findViewById(R.id.btn_update);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPrefs.getString(ActivityProfile.this, SharedPrefs.type).equalsIgnoreCase("Student")) {
                    if (ed_validated()) {
                        registerStudent();
                    }
                }

                if (SharedPrefs.getString(ActivityProfile.this, SharedPrefs.type).equalsIgnoreCase("Faculty")) {
                    if (ed_validatedFaculty()) {
                        registerFaculty();
                    }
                }

            }
        });
        btn_home = (Button) findViewById(R.id.btn_cancel);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (SharedPrefs.getString(ActivityProfile.this, SharedPrefs.type).equalsIgnoreCase("Student")) {
            student = databaseHelper.getStudentDetails();
            et_fname.setText(student.getName());
            et_roll.setText(student.getRollno());
            et_email.setText(student.getEmail());
            et_contact.setText(student.getContact());
            spinner_department.setSelection(getSavedDepartmentPosition(student.getDepartment()));
            spinner_batch.setSelection(getSavedBatchPosition(student.getBatch()));
            et_password.setText(student.getPassword());
            et_fname.setSelection(et_fname.getText().length());
        }

        if (SharedPrefs.getString(ActivityProfile.this, SharedPrefs.type).equalsIgnoreCase("Faculty")) {
            faculty = databaseHelper.getFacultyDetails();
            et_fname.setText(faculty.getName());
            et_roll.setText(faculty.getSubject());
            et_email.setText(faculty.getEmail());
            et_password.setText(faculty.getPassword());
            et_fname.setSelection(et_fname.getText().length());
            spinner_department.setSelection(getSavedDepartmentPosition(faculty.getDepartment()));
            spinner_batch.setVisibility(View.GONE);
            et_contact.setVisibility(View.GONE);
        }

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getSavedDepartmentPosition(String departmentName) {
        if (departmentName.equals(departments[0]))
            return 0;
        if (departmentName.equals(departments[1]))
            return 1;
        if (departmentName.equals(departments[2]))
            return 2;
        if (departmentName.equals(departments[3]))
            return 3;
        if (departmentName.equals(departments[4]))
            return 4;
        if (departmentName.equals(departments[5]))
            return 5;
        return 7;
    }

    private int getSavedBatchPosition(String batchName) {
        if (batchName.equals(batch[0]))
            return 0;
        if (batchName.equals(batch[1]))
            return 1;
        if (batchName.equals(batch[2]))
            return 2;
        if (batchName.equals(batch[3]))
            return 3;
        return 7;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private boolean ed_validated() {

        if (TextUtils.isEmpty(et_fname.getText().toString())) {
            et_fname.setError("Please enter valid First Name");
            et_fname.setFocusable(true);
            et_fname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_roll.getText().toString())) {
            et_roll.setError("Please enter valid Roll No");
            et_roll.setFocusable(true);
            et_roll.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_email.getText().toString()) || !isEmailValid(et_email.getText().toString())) {
            et_email.setError("Please Enter valid Email");
            et_email.setFocusable(true);
            et_email.requestFocus();
            return false;
        } else if (et_contact.getText().toString().trim().length()!=10) {
            et_contact.setError("Please Enter valid Contact Number");
            et_contact.setFocusable(true);
            et_contact.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(et_password.getText().toString())) {
            et_password.setError("Please Enter Password");
            et_password.setFocusable(true);
            et_password.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void registerStudent() {
        student.setName(et_fname.getText().toString());
        student.setRollno(et_roll.getText().toString());
        student.setEmail(et_email.getText().toString());
        student.setPassword(et_password.getText().toString());
        student.setDepartment(departments[spinner_department.getSelectedItemPosition()]);
        student.setBatch(batch[spinner_batch.getSelectedItemPosition()]);
        long i = databaseHelper.addStudent(student);
        if (i != -1) {
            showToastMessage("Profile Updated Successfully");
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.email, student.getEmail());
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.name, student.getName());
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.roll, student.getRollno());
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.type, "Student");
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.department, student.getDepartment());
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.batch, student.getBatch());
            SharedPrefs.setStringData(ActivityProfile.this,SharedPrefs.contact,student.getContact());
            finish();
        }
    }

    private boolean ed_validatedFaculty() {
        if (TextUtils.isEmpty(et_fname.getText().toString())) {
            et_fname.setError("Please enter valid First Name");
            et_fname.setFocusable(true);
            et_fname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_email.getText().toString()) || !isEmailValid(et_email.getText().toString())) {
            et_email.setError("Please Enter valid Email");
            et_email.setFocusable(true);
            et_email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            et_password.setError("Please Enter Password");
            et_password.setFocusable(true);
            et_password.requestFocus();
            return false;
        } else if (et_roll.getText().toString().equalsIgnoreCase("")) {
            showToastMessage("Please Enter Valid Subject");
            return false;
        } else {
            return true;
        }
    }

    public void registerFaculty() {
        faculty.setName(et_fname.getText().toString());
        faculty.setEmail(et_email.getText().toString());
        faculty.setPassword(et_password.getText().toString());
        faculty.setSubject(et_roll.getText().toString());
        long i = databaseHelper.addFaculty(faculty);
        if (i != -1) {
            showToastMessage("Profile Updated Successfully");
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.email, faculty.getEmail());
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.name, faculty.getName());
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.type, "Faculty");
            SharedPrefs.setStringData(ActivityProfile.this, SharedPrefs.subject, faculty.getSubject());

            finish();

        }
    }
}
