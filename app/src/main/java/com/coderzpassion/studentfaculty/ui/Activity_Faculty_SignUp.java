package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class Activity_Faculty_SignUp extends BaseActivity {

    EditText et_fname, et_email, et_contact, et_password, et_password_confirm;
    Button btn_register, btn_home;
    android.support.v7.widget.AppCompatSpinner spinner_department, spinner_subject;

    DatabaseHelper databaseHelper;
    ArrayList<String> departments;
    ArrayList<String> subjects;
    Faculty faculty;
    String actionType;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_signup);
        databaseHelper = new DatabaseHelper(this);

        actionType = getIntent().getStringExtra(Constants.ACTION_TYPE);
        userType = getIntent().getStringExtra(Constants.USER_TYPE);

        departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        subjects = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subjects)));

        et_fname = (EditText) findViewById(R.id.et_fname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_contact = (EditText) findViewById(R.id.et_contact);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_confirm = (EditText) findViewById(R.id.et_confirm_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        spinner_department = (android.support.v7.widget.AppCompatSpinner) findViewById(R.id.department);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(departmentAdapter);

        spinner_subject = (android.support.v7.widget.AppCompatSpinner) findViewById(R.id.subject);
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subject.setAdapter(subjectAdapter);

        if (Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)) {
            spinner_subject.setVisibility(View.GONE);
        }
        if (actionType.equalsIgnoreCase(Constants.ACTION_UPDATE_PROFILE)) {
            et_email.setEnabled(false);
            btn_register.setText("Update");
            updateView();
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_validated()) {
                    registerFaculty();
                }
            }
        });
        btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //if you want to set any action you can do in this listener

       /* try {
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

*/
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
        String password = et_password.getText().toString().trim();
        String confirmPassword = et_password_confirm.getText().toString().trim();
        String selectedDepartment = (String) spinner_department.getSelectedItem();
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
        } else if (et_contact.getText().toString().trim().length() < 10) {
            et_contact.setError("Please Enter valid Mobile number");
            et_contact.setFocusable(true);
            et_contact.requestFocus();
            return false;
        } else if (password.length() < 8) {
            et_password.setError("Password length must be minimum 8 characters");
            et_password.setFocusable(true);
            et_password.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            et_password_confirm.setError("Password do not match");
            et_password_confirm.setFocusable(true);
            et_password_confirm.requestFocus();
            return false;
        } else if (selectedDepartment.equalsIgnoreCase("Select Department")) {
            showToastMessage("Please Select Department");
            return false;
        } else if (Constants.USER_TYPE_FACULTY.equalsIgnoreCase(userType)) {
            String selectedSubject = (String) spinner_subject.getSelectedItem();
            if (selectedSubject.equalsIgnoreCase("Select Subject")) {
                showToastMessage("Please Select Subject");
                return false;
            } else
                return true;
        } else {
            return true;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void registerFaculty() {
        if (!actionType.equalsIgnoreCase(Constants.ACTION_UPDATE_PROFILE)) {
            faculty = new Faculty();
        }
        faculty.setName(et_fname.getText().toString());
        faculty.setEmail(et_email.getText().toString());
        faculty.setContact(et_contact.getText().toString());
        faculty.setPassword(et_password.getText().toString());
        faculty.setDepartment((String) spinner_department.getSelectedItem());
        faculty.setSubject((String) spinner_subject.getSelectedItem());
        long i;
        if (Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)) {
            i = databaseHelper.addHod(faculty);
        } else
            i = databaseHelper.addFaculty(faculty);
        if (i != -1) {
            showToastMessage("Registeration Successful");
            SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.email, faculty.getEmail());
            SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.name, faculty.getName());
            SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.subject, faculty.getSubject());
            SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.department, faculty.getDepartment());
            SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.contact, faculty.getContact());
            Intent intent;
            if (Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)) {
                SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.type, Constants.USER_TYPE_HOD);
                intent = new Intent(Activity_Faculty_SignUp.this, ActivityHodHome.class);
            } else {
                SharedPrefs.setStringData(Activity_Faculty_SignUp.this, SharedPrefs.type, Constants.USER_TYPE_FACULTY);
                intent = new Intent(Activity_Faculty_SignUp.this, Activity_Faculty_Home.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void updateView() {
        if (Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)) {
            faculty = databaseHelper.getHodDetails();
        } else {
            faculty = databaseHelper.getFacultyDetails();
        }
            et_fname.setText(faculty.getName());
            et_email.setText(faculty.getEmail());
            et_contact.setText(faculty.getContact());
            et_password.setText(faculty.getPassword());
            et_password_confirm.setText(faculty.getPassword());
            et_email.setEnabled(false);
            et_email.setFocusable(false);
            et_fname.setSelection(et_fname.getText().length());
            spinner_department.setSelection(departments.indexOf(faculty.getDepartment()));
            spinner_subject.setSelection(subjects.indexOf(faculty.getSubject()));
    }
}
