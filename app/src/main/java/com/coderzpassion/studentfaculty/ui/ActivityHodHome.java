package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.MainActivity;
import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityHodHome extends AppCompatActivity {
    TextView announcements, marks, attendence, datesheet, timetable, logout, welcome, profile, teachers, students;
    Toolbar toolbar;

    ArrayList<String> departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_home);

        departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Constants.USER_TYPE_COLLEGE_HEAD.equalsIgnoreCase(SharedPrefs.getString(this, SharedPrefs.type)))
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>College Head</font>"));
        else
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>HOD</font>"));

        welcome = (TextView) findViewById(R.id.welcome);
        if (SharedPrefs.getString(ActivityHodHome.this, SharedPrefs.name) != null)
            welcome.setText("Welcome " + SharedPrefs.getString(ActivityHodHome.this, SharedPrefs.name));

        AppCompatSpinner departmentSpinner = (AppCompatSpinner) findViewById(R.id.department_spinner);
        announcements = (TextView) findViewById(R.id.announcements);
        announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, Activity_Faculty_Announcement.class);
                startActivity(intent);
            }
        });

        marks = (TextView) findViewById(R.id.marks);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, Activit_Faculty_Marks.class);
                startActivity(intent);
            }
        });
        attendence = (TextView) findViewById(R.id.attendence);
        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, Activity_Faculty_Attendance.class);
                startActivity(intent);
            }
        });
        teachers = (TextView) findViewById(R.id.teachers);
        teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, ActivityViewTeachers.class);
                startActivity(intent);
            }
        });
        students = (TextView) findViewById(R.id.students);
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, ActivityViewStudent.class);
                startActivity(intent);
            }
        });
        timetable = (TextView) findViewById(R.id.timetable);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, TImeTableActivity.class);
                startActivity(intent);
            }
        });
        datesheet = (TextView) findViewById(R.id.datesheet);
        datesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHodHome.this, DatesheetActivity.class);
                startActivity(intent);
            }
        });

        profile = (TextView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (Constants.USER_TYPE_COLLEGE_HEAD.equalsIgnoreCase(SharedPrefs.getString(ActivityHodHome.this, SharedPrefs.type))) {
                    intent = new Intent(ActivityHodHome.this, Activity_CollegeHead_SignUp.class);
                } else
                    intent = new Intent(ActivityHodHome.this, Activity_Faculty_SignUp.class);
                intent.putExtra(Constants.ACTION_TYPE, Constants.ACTION_UPDATE_PROFILE);
                intent.putExtra(Constants.USER_TYPE, Constants.USER_TYPE_HOD);
                startActivity(intent);
            }
        });

        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefs.clear(ActivityHodHome.this);

                Intent intent = new Intent(ActivityHodHome.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
