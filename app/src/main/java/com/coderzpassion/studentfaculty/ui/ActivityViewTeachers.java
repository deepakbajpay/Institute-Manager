package com.coderzpassion.studentfaculty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.adapter.AdapterTeacher;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityViewTeachers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Faculty> faculties;
    ArrayList<String> departments;
    AppCompatSpinner departmentSpinner;
    ArrayAdapter<String> departmentAdapter;
    private DatabaseHelper databaseHelper;
    private String type;
    private AdapterTeacher adapterTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Teachers</font>"));

        Intent intent = getIntent();
        type = intent.getStringExtra(Constants.USER_TYPE);

        databaseHelper = new DatabaseHelper(this);

        departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        departmentSpinner = (AppCompatSpinner) findViewById(R.id.department_spinner);
        departmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);
        departmentSpinner.setOnItemSelectedListener(this);
        faculties = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_teacher);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterTeacher = new AdapterTeacher(this, faculties, type);
        recyclerView.setAdapter(adapterTeacher);
        if (Constants.USER_TYPE_HOD.equalsIgnoreCase(SharedPrefs.getString(this, SharedPrefs.type)))
            departmentSpinner.setSelection(departments.indexOf(SharedPrefs.getString(this, SharedPrefs.department)));

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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.department_spinner:
                faculties.clear();
                if ("Select Department".equalsIgnoreCase((String) departmentSpinner.getSelectedItem())) {
                    if (Constants.USER_TYPE_HOD.equalsIgnoreCase(type)) {
                        faculties.addAll(databaseHelper.getHODs());
                    } else
                        faculties.addAll(databaseHelper.getAllTeachersForAdmin());
                } else {
                    if (Constants.USER_TYPE_HOD.equalsIgnoreCase(type)) {
                        faculties.addAll(databaseHelper.getHodByDepartment((String) departmentSpinner.getSelectedItem()));
                    } else {

                        faculties.addAll(databaseHelper.getTeachersByDepartmentForAdmin((String) departmentSpinner.getSelectedItem()));
                    }
                }
                adapterTeacher.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}