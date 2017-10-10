package com.coderzpassion.studentfaculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.ui.ActivityViewTeachers;
import com.coderzpassion.studentfaculty.ui.CollegeHeadInfo;
import com.coderzpassion.studentfaculty.utils.Constants;

public class ActivityInfo extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    TextView collegeHead,hod,faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Information</font>"));

        collegeHead = (TextView)findViewById(R.id.college_head);
        faculty = (TextView)findViewById(R.id.faculty);
        hod = (TextView)findViewById(R.id.hod);
        collegeHead.setOnClickListener(this);
        hod.setOnClickListener(this);
        faculty.setOnClickListener(this);


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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.college_head:
                intent = new Intent(this, CollegeHeadInfo.class);
                startActivity(intent);
                break;
            case R.id.faculty:
                intent = new Intent(this, ActivityViewTeachers.class);
                intent.putExtra(Constants.USER_TYPE,Constants.USER_TYPE_FACULTY);
                startActivity(intent);
                break;
            case R.id.hod:
                intent = new Intent(this, ActivityViewTeachers.class);
                intent.putExtra(Constants.USER_TYPE,Constants.USER_TYPE_HOD);
                startActivity(intent);
                break;
        }
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
}
