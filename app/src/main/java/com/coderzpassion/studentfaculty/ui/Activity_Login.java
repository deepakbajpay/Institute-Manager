package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.admin.ActivityAdminHome;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class Activity_Login extends BaseActivity {

    EditText et_email, et_password;
    Button bt_login, cancel;
    String loginType;
    TextView LoginType;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(Activity_Login.this);

        loginType = getIntent().getStringExtra("type");
        LoginType = (TextView) findViewById(R.id.logintype);
        LoginType.setText(loginType + " Login");
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_validated()) {
                    if (loginType.equalsIgnoreCase(Constants.USER_TYPE_ADMIN)) {
                        if (et_email.getText().toString().trim().equalsIgnoreCase("Admin@admin.com") && et_password.getText().toString().trim().equalsIgnoreCase("Admin")) {
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.type, Constants.USER_TYPE_ADMIN);
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.email, Constants.USER_TYPE_ADMIN);
                            Intent intent = new Intent(Activity_Login.this, ActivityAdminHome.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else
                            showToastMessage("Your Password or Email is Incorrect");
                    } else if (loginType.equalsIgnoreCase(Constants.USER_TYPE_STUDENT)) {
                        if (databaseHelper.checkUser(et_email.getText().toString().trim(), et_password.getText().toString().trim())) {
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.email, et_email.getText().toString());
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.type, Constants.USER_TYPE_STUDENT);

                            Intent intent = new Intent(Activity_Login.this, Activity_Student_Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            showToastMessage("Your Password or Email is Incorrect");
                        }
                    } else if (loginType.equalsIgnoreCase(Constants.USER_TYPE_FACULTY)) {
                        if (databaseHelper.checkFaculty(et_email.getText().toString().trim(), et_password.getText().toString().trim())) {
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.email, et_email.getText().toString());
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.type, Constants.USER_TYPE_FACULTY);

                            Intent intent = new Intent(Activity_Login.this, Activity_Faculty_Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            showToastMessage("Your Password or Email is Incorrect");

                        }
                    } else if (loginType.equalsIgnoreCase(Constants.USER_TYPE_HOD)) {
                        if (databaseHelper.checkHod(et_email.getText().toString().trim(), et_password.getText().toString().trim())) {
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.email, et_email.getText().toString());
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.type, Constants.USER_TYPE_HOD);

                            Intent intent = new Intent(Activity_Login.this, ActivityHodHome.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else
                            showToastMessage("Your Password or Email is Incorrect");
                    } else if (loginType.equalsIgnoreCase(Constants.USER_TYPE_COLLEGE_HEAD)) {
                        if (databaseHelper.checkCollegeHead(et_email.getText().toString().trim(), et_password.getText().toString().trim())) {
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.email, et_email.getText().toString());
                            SharedPrefs.setStringData(Activity_Login.this, SharedPrefs.type, Constants.USER_TYPE_COLLEGE_HEAD);

                            Intent intent = new Intent(Activity_Login.this, ActivityHodHome.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            showToastMessage("Your Password or Email is Incorrect");
                        }
                    }
                }
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean ed_validated() {
       /* if (TextUtils.isEmpty(et_email.getText().toString()) && !isEmailValid(et_email.getText().toString())) {
            Intent intent = new Intent(Activity_Login.this, ActivityAdminHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return false;
        }*/
        if (TextUtils.isEmpty(et_email.getText().toString()) || !isEmailValid(et_email.getText().toString())) {
            et_email.setError("Please Enter valid Email");
            et_email.setFocusable(true);
            et_email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
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

}
