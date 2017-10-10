package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.MainActivity;
import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class Activity_Faculty_Home extends BaseActivity {

    TextView announcements, marks, attendence, datesheet, timetable, logout, welcome, profile;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Faculty</font>"));

        welcome = (TextView) findViewById(R.id.welcome);
        if (SharedPrefs.getString(Activity_Faculty_Home.this, SharedPrefs.name) != null)
            welcome.setText("Welcome " + SharedPrefs.getString(Activity_Faculty_Home.this, SharedPrefs.name));

        announcements = (TextView) findViewById(R.id.announcements);
        announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Faculty_Home.this, Activity_Faculty_Announcement.class);
                startActivity(intent);
            }
        });

        marks = (TextView) findViewById(R.id.marks);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Faculty_Home.this, Activit_Faculty_Marks.class);
                startActivity(intent);
            }
        });
        attendence = (TextView) findViewById(R.id.attendence);
        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Faculty_Home.this, Activity_Faculty_Attendance.class);
                startActivity(intent);
            }
        });
        timetable = (TextView) findViewById(R.id.timetable);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Faculty_Home.this, TImeTableActivity.class);
                startActivity(intent);
            }
        });
        datesheet = (TextView) findViewById(R.id.datesheet);
        datesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Faculty_Home.this, DatesheetActivity.class);
                startActivity(intent);
            }
        });

        profile = (TextView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                    intent = new Intent(Activity_Faculty_Home.this, Activity_Faculty_SignUp.class);
                intent.putExtra(Constants.ACTION_TYPE, Constants.ACTION_UPDATE_PROFILE);
                intent.putExtra(Constants.USER_TYPE, Constants.USER_TYPE_FACULTY);
                startActivity(intent);
            }
        });

        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefs.clear(Activity_Faculty_Home.this);

                Intent intent = new Intent(Activity_Faculty_Home.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
