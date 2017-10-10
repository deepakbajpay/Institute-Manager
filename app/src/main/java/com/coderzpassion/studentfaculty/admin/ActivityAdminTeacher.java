package com.coderzpassion.studentfaculty.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.adapter.AdapterAdminTeacher;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;

import static com.coderzpassion.studentfaculty.R.array.subjects;

/**
 * Created by coderzpassion on 15/05/17.
 */

public class ActivityAdminTeacher extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    DatabaseHelper databaseHelper;
    ListView allteachers;
    Toolbar toolbar;
    ArrayList<Faculty> faculties;
    AdapterAdminTeacher adapterMarks;

    Spinner searchBySpinner, searchOptionsSpinner;
    String[] searchByOprions = {"All", "Department","Subject"};

    ArrayList<String> departments = new ArrayList<>(Arrays.asList("Computer Science", "Textile", "Civil", "Mechanical", "Electrical", "Electronics and Communication"));
    ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<String> searchOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'> Teachers</font>"));

        databaseHelper=new DatabaseHelper(ActivityAdminTeacher.this);

        searchBySpinner = (Spinner) findViewById(R.id.spinner_search_by);
        ArrayAdapter<String> searchByAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchByOprions);
        searchBySpinner.setAdapter(searchByAdapter);
        searchBySpinner.setOnItemSelectedListener(this);

        searchOptionsSpinner = (Spinner) findViewById(R.id.spinner_search_options);
        searchOptionsList = new ArrayList<>();
        searchOptionsList.addAll(departments);
        searchOptionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchOptionsList);
        searchOptionsSpinner.setAdapter(searchOptionsAdapter);
        searchOptionsSpinner.setOnItemSelectedListener(this);

        allteachers=(ListView)findViewById(R.id.allmarks);
        allteachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlertDialogBox(faculties.get(i));
            }
        });
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
         faculties=databaseHelper.getAllTeachersForAdmin();
         adapterMarks=new AdapterAdminTeacher(ActivityAdminTeacher.this,faculties);
        allteachers.setAdapter(adapterMarks);
    }

    public void showDialog(final Faculty faculty){
        final Dialog dialog = new Dialog(ActivityAdminTeacher.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_admin_editteacher);


        final EditText attendanceObtained = (EditText) dialog.findViewById(R.id.teacher_name);
        attendanceObtained.setText(faculty.getName());
        final Spinner subject = (Spinner) dialog.findViewById(R.id.subject);
        final ArrayList<String> subjects = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subjects)));
        ArrayAdapter<String> subjectByAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects);
        subject.setAdapter(subjectByAdapter);
        subject.setSelection(subjects.indexOf(faculty.getSubject()));

        final ArrayList<String> departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        final Spinner departmentSpinner = (Spinner) dialog.findViewById(R.id.department);
        ArrayAdapter<String> departmentByAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, departments);
        departmentSpinner.setAdapter(departmentByAdapter);
        departmentSpinner.setSelection(departments.indexOf(faculty.getDepartment()));

        Button addmarks = (Button) dialog.findViewById(R.id.addmarks);
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attendanceObtained.getText().toString().equalsIgnoreCase(""))
                {
                    showToastMessage("Please enter valid Attendance obtained");
                }
                else
                {
                    faculty.setName(attendanceObtained.getText().toString());
                    faculty.setSubject(subjects.get(subject.getSelectedItemPosition()));
                    faculty.setDepartment(departments.get(departmentSpinner.getSelectedItemPosition()));
                    faculty.setId(faculty.getId());
                    databaseHelper.addFaculty(faculty);
                    setAdapter();
                    dialog.dismiss();
                }
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showAlertDialogBox(final Faculty markss)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to edit?")
                .setPositiveButton("Edit ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showDialog(markss);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseHelper.deleteTeacher(markss.getEmail());
                        setAdapter();
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId()==R.id.spinner_search_by) {
            switch (i) {
                case 0:
                    searchOptionsSpinner.setVisibility(View.GONE);
                    faculties.addAll(databaseHelper.getAllTeachersForAdmin());
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 1:
                    searchOptionsSpinner.setSelection(0);
                    searchOptionsSpinner.setVisibility(View.VISIBLE);
                    searchOptionsList.clear();
                    searchOptionsList.addAll(departments);

                    searchOptionsAdapter.notifyDataSetChanged();

                    faculties.clear();
                    faculties.addAll(databaseHelper.getTeachersByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();

                    break;
                case 2:
                    searchOptionsSpinner.setSelection(0);
                    searchOptionsSpinner.setVisibility(View.VISIBLE);
                    searchOptionsList.clear();
                    searchOptionsList.addAll(Arrays.asList(getResources().getStringArray(R.array.subjects)));
                    searchOptionsAdapter.notifyDataSetChanged();

                    faculties.clear();
                    faculties.addAll(databaseHelper.getTeachersBySubjectForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();

                    break;
            }
        }
        else if (adapterView.getId()==R.id.spinner_search_options) {
            switch (searchBySpinner.getSelectedItemPosition()){
                case 1:
                    faculties.clear();
                    faculties.addAll(databaseHelper.getTeachersByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 2:
                    faculties.clear();
                    faculties.addAll(databaseHelper.getTeachersBySubjectForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();

                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
