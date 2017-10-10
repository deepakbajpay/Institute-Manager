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

public class ActivityAdminStudentsMarks extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DatabaseHelper databaseHelper;
    ListView allmarks;
    Toolbar toolbar;
    ArrayList<Marks> marks;
    EditText etRollno;
    ImageButton searchButton;
    AdapterAdmin adapterMarks;

    Spinner searchBySpinner, searchOptionsSpinner;
    String[] searchByOprions = {"All", "Department", "Batch", "Roll Number"};

    ArrayList<String> departments;
    ArrayList<String> batch = new ArrayList<>(Arrays.asList("2k14", "2k15", "2k16", "2k17"));
    ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<String> searchOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'> Students Marks</font>"));

        databaseHelper=new DatabaseHelper(ActivityAdminStudentsMarks.this);

        departments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
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

        searchButton = (ImageButton)findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        allmarks=(ListView)findViewById(R.id.allmarks);
        allmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlertDialogBox((Marks) adapterView.getItemAtPosition(i),i);
            }
        });
        setAdapter();
        etRollno =(EditText)findViewById(R.id.et_rollno_admin);
        /*etRollno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(adapterMarks!=null)
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
//        marks=databaseHelper.getAllMarks();
         adapterMarks=new AdapterAdmin(ActivityAdminStudentsMarks.this,marks);
        allmarks.setAdapter(adapterMarks);
    }

    public void showDialog(final Marks markss){
        final Dialog dialog = new Dialog(ActivityAdminStudentsMarks.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_marks);

        final android.support.v7.widget.AppCompatSpinner  subject= (android.support.v7.widget.AppCompatSpinner)dialog.findViewById(R.id.subject);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.subjects));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(adapter);
        subject.setVisibility(View.GONE);

        final EditText marksObtained = (EditText) dialog.findViewById(R.id.marksobtained);
        marksObtained.setText(markss.getMarks_obtained());
        marksObtained.setSelection(marksObtained.getText().length());

        final EditText totalmarks = (EditText) dialog.findViewById(R.id.totalmarks);
        totalmarks.setText(markss.getTotal_marks());

        final EditText rollno = (EditText) dialog.findViewById(R.id.rollno);
        rollno.setText(markss.getMarks_to());

        final Student student=databaseHelper.getStudentDetailsByRollNo(markss.getMarks_to());
        final EditText name = (EditText) dialog.findViewById(R.id.name);
        name.setText(student.getName());

        final EditText email = (EditText) dialog.findViewById(R.id.email);
        email.setText(student.getEmail());
        email.setEnabled(false);

        final EditText contact = (EditText) dialog.findViewById(R.id.contact);
        contact.setText(student.getContact());
        contact.setEnabled(false);



        Button addmarks = (Button) dialog.findViewById(R.id.addmarks);
        addmarks.setText("Update Marks");
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(marksObtained.getText().toString().equalsIgnoreCase(""))
                {
                    showToastMessage("Please enter valid marks obtained");
                }
                else if(totalmarks.getText().toString().equalsIgnoreCase(""))
                {
                    showToastMessage("Please enter valid total marks");
                }
                else if(rollno.getText().toString().equalsIgnoreCase(""))
                {
                    showToastMessage("Please enter valid Roll No");
                }
                else if(name.getText().toString().equalsIgnoreCase(""))
                {
                    showToastMessage("Please enter valid  Name");
                }

                else
                {
                    markss.setMarks_by(markss.getMarks_by());
                    markss.setMarks_obtained(marksObtained.getText().toString());
                    markss.setMarks_to(rollno.getText().toString());
                    markss.setTotal_marks(totalmarks.getText().toString());
                    markss.setSubject(markss.getSubject());
                    student.setName(name.getText().toString());
                    databaseHelper.addMarks(markss);
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

    public void showAlertDialogBox(final Marks markss, final int position)
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
                        databaseHelper.deleteStudentMarks(markss.getId());
                        marks.remove(position);
                        adapterMarks.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_button) {
            switch (searchBySpinner.getSelectedItemPosition()) {
                case 0:
                    marks.clear();
//                    marks.addAll(databaseHelper.getAllMarks());
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 1:
                    marks.clear();
                    marks.addAll(databaseHelper.getMarksByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 2:
                    marks.clear();
                    marks.addAll(databaseHelper.getMarksByBatchForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 3:
                    marks.clear();
                    marks.addAll(databaseHelper.getMyMarks(etRollno.getText().toString().trim()));
                    adapterMarks.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId()==R.id.spinner_search_by) {
            switch (i) {
                case 0:
                    searchOptionsSpinner.setSelection(0);
                    etRollno.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                    searchOptionsSpinner.setVisibility(View.GONE);
                    marks.clear();
//                    marks.addAll(databaseHelper.getAllMarks());
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 1:
                    searchOptionsSpinner.setSelection(0);
                    etRollno.setVisibility(View.GONE);
                    searchOptionsSpinner.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                    searchOptionsList.clear();
                    searchOptionsList.addAll(departments);
                    searchOptionsAdapter.notifyDataSetChanged();

                    marks.clear();
                    marks.addAll(databaseHelper.getMarksByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();

                    break;
                case 2:
                    searchOptionsSpinner.setSelection(0);
                    etRollno.setVisibility(View.GONE);
                    searchOptionsSpinner.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                    searchOptionsList.clear();
                    searchOptionsList.addAll(batch);
                    searchOptionsAdapter.notifyDataSetChanged();

                    marks.clear();
                    marks.addAll(databaseHelper.getMarksByBatchForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();

                    break;
                case 3:
                    searchOptionsSpinner.setSelection(0);
                    etRollno.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    searchOptionsSpinner.setVisibility(View.GONE);
                    break;
            }
        }
        else if (adapterView.getId()==R.id.spinner_search_options) {
            switch (searchBySpinner.getSelectedItemPosition()){
                case 1:
                    marks.clear();
                    marks.addAll(databaseHelper.getMarksByDepartmentForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
                case 2:
                    marks.clear();
                    marks.addAll(databaseHelper.getMarksByBatchForAdmin(searchOptionsList.get(searchOptionsSpinner.getSelectedItemPosition())));
                    adapterMarks.notifyDataSetChanged();
                    break;
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
