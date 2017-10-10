package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
 * Created by coderzpassion on 14/05/17.
 */

public class Activity_Student_SignUp extends BaseActivity {

    EditText et_fname,et_email,et_password,et_confirm_password,et_roll,et_contact;
    Spinner spinner_department,spinner_batch;
    Button btn_register,btn_home;
    DatabaseHelper databaseHelper;
    String[] departments;
    String[] batch;
    String contactFormat = "[1-9]{1}[0-9]{9}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        databaseHelper=new DatabaseHelper(this);
        et_fname=(EditText)findViewById(R.id.et_fname);
        et_roll=(EditText)findViewById(R.id.et_roll);
        et_email=(EditText)findViewById(R.id.et_email);
        et_contact = (EditText)findViewById(R.id.et_contact);
        et_password=(EditText)findViewById(R.id.et_password);
        et_confirm_password=(EditText)findViewById(R.id.et_confirm_password);
        spinner_department = (Spinner) findViewById(R.id.et_department);
        spinner_batch = (Spinner)findViewById(R.id.et_batch);

        departments =getResources().getStringArray(R.array.departments);
        batch = getResources().getStringArray(R.array.batch);

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,departments);
        spinner_department.setAdapter(departmentAdapter);
        ArrayAdapter<String> batchAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,batch);
        spinner_batch.setAdapter(batchAdapter);

        btn_register=(Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_validated())
                {
                    registerStudent();
//                    performRegister();
//                    showToastMessage("This is under development");
                }
            }
        });
        btn_home=(Button)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

    private boolean ed_validated(){
            String contact = et_contact.getText().toString().trim();
        if(TextUtils.isEmpty(et_fname.getText().toString()))
        {
            et_fname.setError("Please enter valid First Name");
            et_fname.setFocusable(true);
            et_fname.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(et_roll.getText().toString()) )
        {
            et_roll.setError("Please enter valid Roll No");
            et_roll.setFocusable(true);
            et_roll.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(et_email.getText().toString()) || !isEmailValid(et_email.getText().toString())){
            et_email.setError("Please Enter valid Email");
            et_email.setFocusable(true);
            et_email.requestFocus();
            return false;
        }
        else if (contact.length()!=10||!contact.matches(contactFormat)){
            et_contact.setError("Please Enter a valid conatct number");
            et_contact.setFocusable(true);
            et_contact.requestFocus();
            return false;
        }
        else if (et_password.getText().toString().length()<8){
            et_password.setError("Password length must be minimum 8 characters");
            et_password.setFocusable(true);
            et_password.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(et_confirm_password.getText().toString())){
            et_confirm_password.setError("Please Enter Password");
            et_confirm_password.setFocusable(true);
            et_confirm_password.requestFocus();
            return false;
        }
        else if (!et_confirm_password.getText().toString().equalsIgnoreCase(et_password.getText().toString())){
            et_confirm_password.setError("Password and Confirm Doesn't Match");
            et_confirm_password.setFocusable(true);
            et_confirm_password.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void registerStudent()
    {
        Student student=new Student();
        student.setName(et_fname.getText().toString());
        student.setRollno(et_roll.getText().toString());
        student.setEmail(et_email.getText().toString());
        student.setContact(et_contact.getText().toString());
        System.out.println("Activity_Student_SignUp.registerStudent " +et_contact.getText().toString());
        student.setPassword(et_password.getText().toString());
        student.setDepartment(departments[spinner_department.getSelectedItemPosition()]);
        student.setBatch(batch[spinner_batch.getSelectedItemPosition()]);

        long i= databaseHelper.addStudent(student);
        if(i!=-1)
        {
            showToastMessage("Registeration Successful");
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.email,student.getEmail());
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.name,student.getName());
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.roll,student.getRollno());
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.type, Constants.USER_TYPE_STUDENT);
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.department,student.getDepartment());
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.batch,student.getBatch());
            SharedPrefs.setStringData(Activity_Student_SignUp.this,SharedPrefs.contact,student.getContact());
            Intent intent=new Intent(Activity_Student_SignUp.this,Activity_Student_Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
