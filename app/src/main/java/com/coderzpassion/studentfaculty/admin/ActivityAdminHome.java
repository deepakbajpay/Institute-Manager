package com.coderzpassion.studentfaculty.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.MainActivity;
import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.ui.Activit_Faculty_Marks;
import com.coderzpassion.studentfaculty.ui.ActivityViewStudent;
import com.coderzpassion.studentfaculty.ui.ActivityViewTeachers;
import com.coderzpassion.studentfaculty.ui.Activity_Faculty_Announcement;
import com.coderzpassion.studentfaculty.ui.Activity_Faculty_Attendance;
import com.coderzpassion.studentfaculty.ui.DatesheetActivity;
import com.coderzpassion.studentfaculty.ui.TImeTableActivity;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

/**
 * Created by coderzpassion on 15/05/17.
 */

public class ActivityAdminHome extends BaseActivity {

    TextView marks,attendence,logout,welcome,teachers,students,datesheet,timetable,annoucement;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Admin</font>"));

        welcome=(TextView)findViewById(R.id.welcome);
        welcome.setText("Welcome Admin");

        annoucement=(TextView)findViewById(R.id.annoucement);
        annoucement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,Activity_Faculty_Announcement.class);
                startActivity(intent);
            }
        });
        
        teachers=(TextView)findViewById(R.id.teachers);
        teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,ActivityViewTeachers.class);
                startActivity(intent);
            }
        });
        students = (TextView)findViewById(R.id.students);
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,ActivityViewStudent.class);
                startActivity(intent);
            }
        });

        marks=(TextView)findViewById(R.id.marks);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,Activit_Faculty_Marks.class);
                startActivity(intent);
            }
        });
        attendence=(TextView)findViewById(R.id.attendence);
        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,ActivityAdminStudentsAttendance.class);
                startActivity(intent);
            }
        });

        datesheet=(TextView)findViewById(R.id.datesheet);
        datesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,DatesheetActivity.class);
                startActivity(intent);
            }
        });

        timetable=(TextView)findViewById(R.id.timetable);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,TImeTableActivity.class);
                startActivity(intent);
            }
        });
        attendence=(TextView)findViewById(R.id.attendence);
        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityAdminHome.this,Activity_Faculty_Attendance.class);
                startActivity(intent);
            }
        });

        logout=(TextView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefs.clear(ActivityAdminHome.this);

                Intent intent=new Intent(ActivityAdminHome.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}
