package com.coderzpassion.studentfaculty.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.coderzpassion.studentfaculty.adapter.AdapterAdmin;
import com.coderzpassion.studentfaculty.model.Marks;
import com.coderzpassion.studentfaculty.model.Student;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by coderzpassion on 15/05/17.
 */

public class ActivityAdminStudentsAttendance extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DatabaseHelper databaseHelper;
    ListView allAttendance;
    Toolbar toolbar;
    ArrayList<Marks> attendance;
    EditText etRollno;
    ImageButton searchButton;
    AdapterAdmin adapterMarks;
    Spinner searchBySpinner, searchOptionsSpinner;
    String[] searchByOprions = {"All", "Department", "Batch", "Roll Number"};

    ArrayList<String> departments;
    ArrayList<String> batch;
    ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<String> searchOptionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'> Students Attendance</font>"));

        databaseHelper = new DatabaseHelper(ActivityAdminStudentsAttendance.this);

        departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        batch = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.batch)));

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
        allAttendance = (ListView) findViewById(R.id.allmarks);
        allAttendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlertDialogBox(attendance.get(i));
            }
        });
        setAdapter();
        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        etRollno = (EditText) findViewById(R.id.et_rollno_admin);
        /*etRollno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapterMarks != null)
                    adapterMarks.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

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
    public void onClick(View view) {
        if (view.getId() == R.id.search_button) {
            switch (searchBySpinner.getSelectedItemPosition()) {
                case 0:
                    searchOptionsSpinner.setSelection(0);
                    attendance.clear();
                    searchButton.setVisibility(View.GONE);
                    attendance.addAll(databaseHelper.getAllAttendanceForAdmin());
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 1:
                    searchOptionsSpinner.setSelection(0);
                    attendance.clear();
                    searchButton.setVisibility(View.GONE);
                    attendance.addAll(databaseHelper.getAttendanceByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 2:
                    searchOptionsSpinner.setSelection(0);
                    attendance.clear();
                    searchButton.setVisibility(View.GONE);
                    attendance.addAll(databaseHelper.getAttendanceByBatchForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 3:
                    searchOptionsSpinner.setSelection(0);
                    attendance.clear();
                    attendance.addAll(databaseHelper.getMyAttendance(etRollno.getText().toString().trim()));
                    adapterMarks.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinner_search_by) {
            switch (i) {
                case 0:
                    etRollno.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                    searchOptionsSpinner.setVisibility(View.GONE);
                    attendance.clear();
                    attendance.addAll(databaseHelper.getAllAttendanceForAdmin());
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 1:
                    etRollno.setVisibility(View.GONE);
                    searchOptionsSpinner.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                    searchOptionsList.clear();
                    searchOptionsList.addAll(departments);
                    searchOptionsAdapter.notifyDataSetChanged();
                    attendance.clear();
                    attendance.addAll(databaseHelper.getAttendanceByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 2:
                    etRollno.setVisibility(View.GONE);
                    searchOptionsSpinner.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                    searchOptionsList.clear();
                    searchOptionsList.addAll(batch);
                    searchOptionsAdapter.notifyDataSetChanged();
                    attendance.clear();
                    attendance.addAll(databaseHelper.getAttendanceByBatchForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 3:
                    etRollno.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    searchOptionsSpinner.setVisibility(View.GONE);
                    break;
            }
        }
        else if (adapterView.getId()==R.id.spinner_search_options) {
            switch (searchBySpinner.getSelectedItemPosition()){
                case 1:
                    attendance.clear();
                    attendance.addAll(databaseHelper.getAttendanceByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 2:
                    attendance.clear();
                    attendance.addAll(databaseHelper.getAttendanceByBatchForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void setAdapter() {
        attendance = databaseHelper.getAllAttendanceForAdmin();

        adapterMarks = new AdapterAdmin(ActivityAdminStudentsAttendance.this, attendance);
        allAttendance.setAdapter(adapterMarks);
    }

    public void showDialog(final Marks attendance) {
        final Dialog dialog = new Dialog(ActivityAdminStudentsAttendance.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_attendence);

        final android.support.v7.widget.AppCompatSpinner subject = (android.support.v7.widget.AppCompatSpinner) dialog.findViewById(R.id.subject);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.subjects));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(adapter);

        final EditText attendanceObtained = (EditText) dialog.findViewById(R.id.marksobtained);
        attendanceObtained.setText(attendance.getMarks_obtained());
        attendanceObtained.setSelection(attendanceObtained.getText().length());

        final EditText totalattendance = (EditText) dialog.findViewById(R.id.totalmarks);
        totalattendance.setText(attendance.getTotal_marks());
        final EditText rollno = (EditText) dialog.findViewById(R.id.rollno);
        rollno.setText(attendance.getMarks_to());

        final Student student = databaseHelper.getStudentDetailsByRollNo(attendance.getMarks_to());
        final EditText name = (EditText) dialog.findViewById(R.id.name);
        name.setText(student.getName());

        final EditText email = (EditText) dialog.findViewById(R.id.email);
        email.setText(student.getEmail());
        email.setEnabled(false);

        final EditText contact = (EditText) dialog.findViewById(R.id.contact);
        contact.setText(student.getContact());
        contact.setEnabled(false);

        Button addmarks = (Button) dialog.findViewById(R.id.addmarks);
        addmarks.setText("Update Attendance");
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attendanceObtained.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid Attendance obtained");
                } else if (totalattendance.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid total Attendance");
                } else if (rollno.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid Roll No");
                } else if (name.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid  Name");
                } else {
                    attendance.setMarks_by(attendance.getMarks_by());
                    attendance.setMarks_obtained(attendanceObtained.getText().toString());
                    attendance.setMarks_to(rollno.getText().toString());
                    attendance.setTotal_marks(totalattendance.getText().toString());
                    attendance.setSubject(attendance.getSubject());
                    student.setName(name.getText().toString());
                    databaseHelper.addAttendance(attendance);
                    databaseHelper.addStudent(student);
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

    public void showAlertDialogBox(final Marks markss) {
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
                        databaseHelper.deleteStudentAttendance(markss.getId());
                        setAdapter();
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
