package com.coderzpassion.studentfaculty.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.adapter.AdapterMarks;
import com.coderzpassion.studentfaculty.model.Marks;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.util.ArrayList;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class ActivityStudentAttendance extends BaseActivity {

    Button addmarks;
    DatabaseHelper databaseHelper;
    ListView allmarks;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Attendance</font>"));

        databaseHelper=new DatabaseHelper(ActivityStudentAttendance.this);

        addmarks=(Button)findViewById(R.id.addmarks);
        addmarks.setVisibility(View.GONE);


        allmarks=(ListView)findViewById(R.id.allmarks);
        setAdapter();
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
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void setAdapter()
    {
        ArrayList<Marks> markses=databaseHelper.getMyAttendance(SharedPrefs.getString(this,SharedPrefs.roll));
        AdapterMarks adapterMarks=new AdapterMarks(ActivityStudentAttendance.this,markses, Constants.TYPE_ATTENDANCE);
        allmarks.setAdapter(adapterMarks);
    }

}
