package com.coderzpassion.studentfaculty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.coderzpassion.studentfaculty.admin.ActivityAdminHome;
import com.coderzpassion.studentfaculty.ui.ActivityHodHome;
import com.coderzpassion.studentfaculty.ui.Activity_CollegeHead_SignUp;
import com.coderzpassion.studentfaculty.ui.Activity_Faculty_Home;
import com.coderzpassion.studentfaculty.ui.Activity_Faculty_SignUp;
import com.coderzpassion.studentfaculty.ui.Activity_Login;
import com.coderzpassion.studentfaculty.ui.Activity_Student_Home;
import com.coderzpassion.studentfaculty.ui.Activity_Student_SignUp;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    android.support.v7.widget.AppCompatSpinner designationtype;
    Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String type= SharedPrefs.getString(MainActivity.this,SharedPrefs.type);
        if(type!=null)
        {
            if(type.equalsIgnoreCase(Constants.USER_TYPE_STUDENT))
            {
                Intent intent=new Intent(MainActivity.this, Activity_Student_Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            if(type.equalsIgnoreCase(Constants.USER_TYPE_FACULTY))
            {
                Intent intent=new Intent(MainActivity.this, Activity_Faculty_Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            if (type.equalsIgnoreCase(Constants.USER_TYPE_ADMIN)){
                Intent intent=new Intent(MainActivity.this,ActivityAdminHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            if (type.equalsIgnoreCase(Constants.USER_TYPE_HOD)){
                Intent intent=new Intent(MainActivity.this,ActivityHodHome.class);
                intent.putExtra(Constants.USER_TYPE,Constants.USER_TYPE_HOD);
                startActivity(intent);
                finish();
            }
            if (type.equalsIgnoreCase(Constants.USER_TYPE_COLLEGE_HEAD)){
                Intent intent=new Intent(MainActivity.this,ActivityHodHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);

        designationtype= (android.support.v7.widget.AppCompatSpinner) findViewById(R.id.designationtype);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.designation));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designationtype.setAdapter(adapter);
        //if you want to set any action you can do in this listener
        designationtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                if (position==3){
                    register.setVisibility(View.GONE);
                }else
                    register.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Button info = (Button)findViewById(R.id.btn_info);
        info.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_info){
            Intent intent = new Intent(this,ActivityInfo.class);
            startActivity(intent);
            return;
        }
        String selected=(String) designationtype.getSelectedItem();
        if(selected.equalsIgnoreCase("Select Designation"))
        {
            showToastMessage("Please Select Valid Designation");
            return;
        }

        if(view.getId()==R.id.login)
        {
            Intent intent=new Intent(MainActivity.this, Activity_Login.class);
            intent.putExtra("type",selected);
             startActivity(intent);
        }

        if(view.getId()==R.id.register)
        {
            if(selected.equalsIgnoreCase("Faculty"))
            {
                Intent intent=new Intent(MainActivity.this, Activity_Faculty_SignUp.class);
                intent.putExtra(Constants.ACTION_TYPE,Constants.ACTION_REGISTER);
                intent.putExtra(Constants.USER_TYPE,Constants.USER_TYPE_FACULTY);
                startActivity(intent);
            }
            if(selected.equalsIgnoreCase("Student"))
            {
                Intent intent=new Intent(MainActivity.this, Activity_Student_SignUp.class);
                startActivity(intent);
            }
            if (selected.equalsIgnoreCase("HOD")){
                Intent intent=new Intent(MainActivity.this,Activity_Faculty_SignUp.class);
                intent.putExtra(Constants.ACTION_TYPE,Constants.ACTION_REGISTER);
                intent.putExtra(Constants.USER_TYPE,Constants.USER_TYPE_HOD);
                startActivity(intent);
            }
            if (selected.equalsIgnoreCase("College Head")){
                Intent intent=new Intent(MainActivity.this,Activity_CollegeHead_SignUp.class);
                intent.putExtra(Constants.ACTION_TYPE,Constants.ACTION_REGISTER);
                startActivity(intent);
            }
        }


    }
}
