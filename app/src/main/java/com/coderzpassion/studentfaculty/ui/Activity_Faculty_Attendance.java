package com.coderzpassion.studentfaculty.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.adapter.AdapterMarks;
import com.coderzpassion.studentfaculty.model.Marks;
import com.coderzpassion.studentfaculty.model.Student;
import com.coderzpassion.studentfaculty.utils.BaseActivity;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class Activity_Faculty_Attendance extends BaseActivity implements AdapterView.OnItemSelectedListener {

    Button addmarks;
    DatabaseHelper databaseHelper;
    ListView allmarks;
    Toolbar toolbar;
    Spinner spinnerDepartment, spinnerSubject, spinnerMst, spinnerBatch;
    String[] batchList, mstList = {"MST-1", "MST-2"};
    ArrayList<String> departmentList, subjectList;
    ArrayList<Marks> markses,originalMarks;
    String userType;
    AdapterMarks adapterMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_marks);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Attendance</font>"));

        userType = SharedPrefs.getString(this, SharedPrefs.type);
        markses = new ArrayList<>();

        databaseHelper = new DatabaseHelper(Activity_Faculty_Attendance.this);

        departmentList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        batchList = getResources().getStringArray(R.array.batch);
        subjectList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subjects)));

        addmarks = (Button) findViewById(R.id.addmarks);
        addmarks.setText("Add Attendance");
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        if (!userType.equalsIgnoreCase(Constants.USER_TYPE_FACULTY)) {
            addmarks.setVisibility(View.GONE);
        }

        spinnerDepartment = (Spinner) findViewById(R.id.marks_department_spinner);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, departmentList);
        spinnerDepartment.setAdapter(departmentAdapter);
        spinnerDepartment.setOnItemSelectedListener(this);

        spinnerBatch = (Spinner) findViewById(R.id.marks_batch_spinner);
        ArrayAdapter<String> batchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, batchList);
        spinnerBatch.setAdapter(batchAdapter);
        spinnerBatch.setOnItemSelectedListener(this);

        spinnerMst = (Spinner) findViewById(R.id.marks_mst_spinner);


        spinnerMst.setVisibility(View.GONE);

        spinnerSubject = (Spinner) findViewById(R.id.marks_subject_spinner);
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectList);
        spinnerSubject.setAdapter(subjectAdapter);
        spinnerSubject.setOnItemSelectedListener(this);
        spinnerSubject.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        if (Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)) {
//            spinnerDepartment.setSelection(departmentList.indexOf(SharedPrefs.getString(this, SharedPrefs.department)));
//            spinnerDepartment.setEnabled(false);
//            spinnerDepartment.setClickable(false);
//        }
        if (Constants.USER_TYPE_FACULTY.equalsIgnoreCase(userType)) {
            spinnerSubject.setSelection(subjectList.indexOf(SharedPrefs.getString(this, SharedPrefs.subject)));
            spinnerSubject.setEnabled(false);
            spinnerSubject.setClickable(false);
        }

        allmarks = (ListView) findViewById(R.id.allmarks);
        adapterMarks = new AdapterMarks(Activity_Faculty_Attendance.this, markses, Constants.TYPE_ATTENDANCE);
        allmarks.setAdapter(adapterMarks);

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

    public void showDialog() {
        final Dialog dialog = new Dialog(Activity_Faculty_Attendance.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_attendence);

        String[] subjectList = {SharedPrefs.getString(Activity_Faculty_Attendance.this, SharedPrefs.subject)};
        final android.support.v7.widget.AppCompatSpinner subject = (android.support.v7.widget.AppCompatSpinner) dialog.findViewById(R.id.subject);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(adapter);
        /*String compareValue =SharedPrefs.getString(Activity_Faculty_Attendance.this,SharedPrefs.subject);

        if (compareValue!=null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            subject.setSelection(spinnerPosition);
        }*/

//        String[] msts = {"MST-1","MST-2"};
        final android.support.v7.widget.AppCompatSpinner spinnerMst = (android.support.v7.widget.AppCompatSpinner) dialog.findViewById(R.id.mst);//fetch the spinner from layout file
//        ArrayAdapter<String> adapterMst = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, msts);//setting the country_array to spinner
//        adapterMst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerMst.setAdapter(adapterMst);
        spinnerMst.setVisibility(View.GONE);

        LinearLayout namelay = (LinearLayout) dialog.findViewById(R.id.namelay);
        namelay.setVisibility(View.GONE);
        LinearLayout emaillay = (LinearLayout) dialog.findViewById(R.id.emaillay);
        emaillay.setVisibility(View.GONE);
        LinearLayout contactlay = (LinearLayout) dialog.findViewById(R.id.contactlay);
        contactlay.setVisibility(View.GONE);

        final EditText marksObtained = (EditText) dialog.findViewById(R.id.marksobtained);
        final EditText totalmarks = (EditText) dialog.findViewById(R.id.totalmarks);
        final EditText rollno = (EditText) dialog.findViewById(R.id.rollno);

        Button addmarks = (Button) dialog.findViewById(R.id.addmarks);
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String obtainedMarks = marksObtained.getText().toString().trim();
                String total = totalmarks.getText().toString().trim();

                if (marksObtained.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid Attendance obtained");
                } else if (totalmarks.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid total Attendance");
                } else if (rollno.getText().toString().equalsIgnoreCase("")) {
                    showToastMessage("Please enter valid Roll No");
                } else if ((subject.getItemAtPosition(subject.getSelectedItemPosition()).toString().trim()).equalsIgnoreCase("Select Subject")) {
                    showToastMessage("Please select valid subject");
                } else if (Integer.parseInt(total) < Integer.parseInt(obtainedMarks)) {
                    Toast.makeText(Activity_Faculty_Attendance.this, "Obtained attendance can't be more than total attendance.", Toast.LENGTH_SHORT).show();
                } else {
                    Marks marks = new Marks();
                    marks.setMarks_by(SharedPrefs.getString(Activity_Faculty_Attendance.this, SharedPrefs.email));
                    marks.setMarks_obtained(marksObtained.getText().toString());
                    marks.setMarks_to(rollno.getText().toString());
                    marks.setTotal_marks(totalmarks.getText().toString());
                    marks.setSubject((String) subject.getSelectedItem());
//                    marks.setMst((String)spinnerMst.getSelectedItem());
                    databaseHelper.addAttendance(marks);
                    setAdapter();

                    marksObtained.setText("");
                    totalmarks.setText("");
                    rollno.setText("");
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

    public void setAdapter() {
        originalMarks = databaseHelper.getAllAttendance(
                (String) spinnerDepartment.getSelectedItem()
                , (String) spinnerBatch.getSelectedItem()
                , (String) spinnerSubject.getSelectedItem());
        markses.clear();
        markses.addAll(filterMarks());

        adapterMarks.notifyDataSetChanged();

        if (Constants.USER_TYPE_ADMIN.equalsIgnoreCase(SharedPrefs.getString(this, SharedPrefs.type))) {
            allmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showAlertDialogBox((Marks) adapterView.getItemAtPosition(i), i);
                }
            });
        }
    }

    private ArrayList<Marks> filterMarks() {
        ArrayList<Marks> marksList = new ArrayList<>();
        marksList.addAll(originalMarks);
        String subject = (String) spinnerSubject.getSelectedItem();
        if (!"Select Subject".equalsIgnoreCase(subject)) {
            for (Iterator<Marks> it = marksList.iterator(); it.hasNext(); ) {
                Marks marks = it.next();
                if (!marks.getSubject().equalsIgnoreCase(subject))
                    it.remove();
            }
        }
        return marksList;
    }

    public void showAlertDialogBox(final Marks markss, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to edit?")
                .setPositiveButton("Edit ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showDialogForUpdate(markss);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseHelper.deleteStudentMarks(markss.getId());
                        markses.remove(position);
                        adapterMarks.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showDialogForUpdate(final Marks markss) {
        final Dialog dialog = new Dialog(Activity_Faculty_Attendance.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_marks);

        ArrayList<String> subjectList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subjects)));

        final android.support.v7.widget.AppCompatSpinner spinnerUpdateSubject = (android.support.v7.widget.AppCompatSpinner) dialog.findViewById(R.id.subject);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateSubject.setAdapter(adapter);
        spinnerUpdateSubject.setSelection(subjectList.indexOf(markss.getSubject()));
        spinnerUpdateSubject.setEnabled(false);
        spinnerUpdateSubject.setClickable(false);

//        ArrayList<String> msts = new ArrayList<>(Arrays.asList("MST-1","MST-2"));
        final android.support.v7.widget.AppCompatSpinner spinnerMst = (android.support.v7.widget.AppCompatSpinner) dialog.findViewById(R.id.mst);//fetch the spinner from layout file
//        ArrayAdapter<String> adapterMst = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, msts);//setting the country_array to spinner
//        adapterMst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerMst.setAdapter(adapterMst);
        spinnerMst.setVisibility(View.GONE);
//        spinnerMst.setSelection(msts.indexOf(markss.getMst()));

        final EditText marksObtained = (EditText) dialog.findViewById(R.id.marksobtained);
        marksObtained.setText(markss.getMarks_obtained());
        marksObtained.setSelection(marksObtained.getText().length());

        final EditText totalmarks = (EditText) dialog.findViewById(R.id.totalmarks);
        totalmarks.setText(markss.getTotal_marks());

        final EditText rollno = (EditText) dialog.findViewById(R.id.rollno);
        rollno.setText(markss.getMarks_to());

        final Student student = databaseHelper.getStudentDetailsByRollNo(markss.getMarks_to());
        final EditText name = (EditText) dialog.findViewById(R.id.name);
        name.setText(student.getName());

        final EditText email = (EditText) dialog.findViewById(R.id.email);
        email.setText(student.getEmail());
        email.setEnabled(false);

        final EditText contact = (EditText) dialog.findViewById(R.id.contact);
        contact.setText(student.getContact());
        contact.setEnabled(false);

        TextView labelObtainedMarks = (TextView)dialog.findViewById(R.id.obtained_marks_label);
        labelObtainedMarks.setText("Attendance Obtained");

        TextView labelTotalMarks = (TextView)dialog.findViewById(R.id.total_marks_label);
        labelObtainedMarks.setText("Total Attendance");

        LinearLayout namelay = (LinearLayout) dialog.findViewById(R.id.namelay);
        namelay.setVisibility(View.GONE);
        LinearLayout emaillay = (LinearLayout) dialog.findViewById(R.id.emaillay);
        emaillay.setVisibility(View.GONE);
        LinearLayout contactlay = (LinearLayout) dialog.findViewById(R.id.contactlay);
        contactlay.setVisibility(View.GONE);

        Button addmarks = (Button) dialog.findViewById(R.id.addmarks);
        addmarks.setText("Update Attendance");
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String obtained = marksObtained.getText().toString().trim();
                String total = totalmarks.getText().toString().trim();
                if (obtained.equalsIgnoreCase("")) {
                } else if (total.equalsIgnoreCase("")) {
                /*}else if ("Select Subject".equalsIgnoreCase((String)spinnerUpdateSubject.getSelectedItem())){
                    showToastMessage("Please select subject");*/
                }else if (Integer.parseInt(total)<Integer.parseInt(obtained)) {
                    Toast.makeText(Activity_Faculty_Attendance.this, "Obtained attendance can't be more than total attendance", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    markss.setMarks_by(markss.getMarks_by());
                    markss.setMarks_obtained(marksObtained.getText().toString());
                    markss.setMarks_to(rollno.getText().toString());
                    markss.setTotal_marks(totalmarks.getText().toString());
//                    markss.setSubject((String)spinnerUpdateSubject.getSelectedItem());
                    markss.setMst((String) spinnerMst.getSelectedItem());
                    databaseHelper.addAttendance(markss);
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        setAdapter();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}