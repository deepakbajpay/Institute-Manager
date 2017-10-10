package com.coderzpassion.studentfaculty.ui;

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
import com.coderzpassion.studentfaculty.adapter.AdapterStudent;
import com.coderzpassion.studentfaculty.model.Student;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ActivityViewStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Student> faculties;
    ArrayList<String> departments;
    AppCompatSpinner departmentSpinner;
    ArrayAdapter<String> departmentAdapter;
    private DatabaseHelper databaseHelper;
    private AdapterStudent adapterStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Students</font>"));

        databaseHelper = new DatabaseHelper(this);
        String userType = SharedPrefs.getString(this, SharedPrefs.type);

        departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        departmentSpinner = (AppCompatSpinner) findViewById(R.id.department_spinner);
        departmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);
        departmentSpinner.setOnItemSelectedListener(this);

        if (Constants.USER_TYPE_FACULTY.equalsIgnoreCase(userType) || Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)) {
            departmentSpinner.setSelection(departments.indexOf(SharedPrefs.getString(this, SharedPrefs.department)));
            departmentSpinner.setEnabled(false);
            departmentSpinner.setClickable(false);
        }
        faculties = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_student);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterStudent = new AdapterStudent(this, faculties);
        recyclerView.setAdapter(adapterStudent);
        System.out.println("ActivityViewStudents.onCreate " + databaseHelper.getAllStudents().size());
        if (SharedPrefs.getString(this, SharedPrefs.type).equalsIgnoreCase(Constants.USER_TYPE_HOD))
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
                faculties.addAll(databaseHelper.getAllStudents());
                if (!"Select Department".equalsIgnoreCase((String) departmentSpinner.getSelectedItem())) {
                    filterRestultsForDepartment();
                }
                adapterStudent.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void filterRestultsForDepartment() {
        String selectedDepartment = (String) departmentSpinner.getSelectedItem();
        for (Iterator<Student> it = faculties.iterator(); it.hasNext(); ) {
            Student student = it.next();
            if (!student.getDepartment().equalsIgnoreCase(selectedDepartment)) {
                it.remove();
            }
        }

    }
}
