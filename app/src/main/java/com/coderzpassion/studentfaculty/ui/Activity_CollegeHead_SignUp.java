package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

public class Activity_CollegeHead_SignUp extends BaseActivity {

    EditText et_fname, et_email, et_contact, et_password, et_password_confirm;
    Button btn_register, btn_home;
    String actionType;
    private Faculty faculty;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__college_head__sign_up);

        databaseHelper = new DatabaseHelper(this);

        actionType = getIntent().getStringExtra(Constants.ACTION_TYPE);

        et_fname = (EditText) findViewById(R.id.et_fname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_contact = (EditText) findViewById(R.id.et_contact);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_confirm = (EditText) findViewById(R.id.et_confirm_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        if (Constants.ACTION_UPDATE_PROFILE.equalsIgnoreCase(actionType))
            updateView();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_validated()) {
                    registerCollegeHead();
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

    }

    private boolean ed_validated() {
        String password = et_password.getText().toString().trim();
        String confirmPassword = et_password_confirm.getText().toString().trim();

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
        } else {
            return true;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void registerCollegeHead() {
        if (!actionType.equalsIgnoreCase(Constants.ACTION_UPDATE_PROFILE)) {
            faculty = new Faculty();
        }
        faculty.setName(et_fname.getText().toString());
        faculty.setEmail(et_email.getText().toString());
        faculty.setContact(et_contact.getText().toString());
        faculty.setPassword(et_password.getText().toString());

        long i;
        i = databaseHelper.addCollegeHead(faculty);
        if (i != -1) {
            showToastMessage("Registeration Successful");
            SharedPrefs.setStringData(Activity_CollegeHead_SignUp.this, SharedPrefs.email, faculty.getEmail());
            SharedPrefs.setStringData(Activity_CollegeHead_SignUp.this, SharedPrefs.name, faculty.getName());
            SharedPrefs.setStringData(Activity_CollegeHead_SignUp.this, SharedPrefs.subject, faculty.getSubject());
            SharedPrefs.setStringData(Activity_CollegeHead_SignUp.this, SharedPrefs.contact, faculty.getContact());
            SharedPrefs.setStringData(Activity_CollegeHead_SignUp.this, SharedPrefs.type, Constants.USER_TYPE_COLLEGE_HEAD);

            Intent intent;
            intent = new Intent(Activity_CollegeHead_SignUp.this, ActivityHodHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }

    private void updateView() {
        btn_register.setText("Update");
        System.out.println("Activity_CollegeHead_SignUp.updateView");
        faculty = databaseHelper.getCollegeHeadDetails();
        et_fname.setText(faculty.getName());
        et_email.setText(faculty.getEmail());
        et_contact.setText(faculty.getContact());
        et_password.setText(faculty.getPassword());
        et_password_confirm.setText(faculty.getPassword());
        et_email.setEnabled(false);
        et_email.setFocusable(false);
        et_fname.setSelection(et_fname.getText().length());

    }
}
