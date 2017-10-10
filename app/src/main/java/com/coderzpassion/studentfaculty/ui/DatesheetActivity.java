package com.coderzpassion.studentfaculty.ui;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.coderzpassion.studentfaculty.MainActivity;
import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.adapter.AdapterDateSheet;
import com.coderzpassion.studentfaculty.model.DatesheetItem;
import com.coderzpassion.studentfaculty.utils.Constants;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;
import com.coderzpassion.studentfaculty.utils.SharedPrefs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DatesheetActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    RecyclerView datesheetRecycler;
    Button addButton;
    Spinner spinnerDepartment,spinnerBatch;

    AdapterDateSheet adapterDateSheetAdapter;

    ArrayList<String> departments;
    ArrayList<String> batch;
    ArrayList<DatesheetItem> datesheetItems;
    DatabaseHelper databaseHelper;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datesheet);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Students</font>"));

        databaseHelper= new DatabaseHelper(DatesheetActivity.this);
        userType = SharedPrefs.getString(this,SharedPrefs.type);

        departments =new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.departments)));
        batch = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.batch)));

        LinearLayoutManager addDocumentManager = new LinearLayoutManager(this);

        addButton = (Button) findViewById(R.id.datesheet_add_btn);
        spinnerBatch = (Spinner)findViewById(R.id.datesheet_batch_spinner);
        spinnerDepartment = (Spinner)findViewById(R.id.datesheet_department_spinner);
        datesheetRecycler = (RecyclerView)findViewById(R.id.datesheet_recycler);
        datesheetRecycler.setLayoutManager(addDocumentManager);
        addButton.setOnClickListener(this);
        if (!"Admin".equalsIgnoreCase(userType)){
            addButton.setVisibility(View.GONE);
        }

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,departments);
        spinnerDepartment.setAdapter(departmentAdapter);
        ArrayAdapter<String> batchAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.batch));
        spinnerBatch.setAdapter(batchAdapter);
        spinnerBatch.setOnItemSelectedListener(this);
        spinnerDepartment.setOnItemSelectedListener(this);

        datesheetItems = new ArrayList<>();
        datesheetItems.addAll(databaseHelper.getDateSheet((String)spinnerDepartment.getSelectedItem(),(String)spinnerBatch.getSelectedItem()));
        System.out.println("DatesheetActivity.onCreate " + datesheetItems.size());
        adapterDateSheetAdapter = new AdapterDateSheet(this,datesheetItems);
        datesheetRecycler.setAdapter(adapterDateSheetAdapter);

        if (Constants.USER_TYPE_FACULTY.equalsIgnoreCase(userType)||Constants.USER_TYPE_HOD.equalsIgnoreCase(userType)){
            spinnerDepartment.setSelection(departments.indexOf(SharedPrefs.getString(this,SharedPrefs.department)));
        }
        if (Constants.USER_TYPE_STUDENT.equalsIgnoreCase(userType)){
            spinnerDepartment.setSelection(departments.indexOf(SharedPrefs.getString(this,SharedPrefs.department)));
            spinnerBatch.setSelection(batch.indexOf(SharedPrefs.getString(this,SharedPrefs.batch)));
        }


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
        if (((String)spinnerDepartment.getSelectedItem()).equalsIgnoreCase("Select Department")){
            Toast.makeText(DatesheetActivity.this, "Please select department", Toast.LENGTH_SHORT).show();
            return;
        }else if (((String)spinnerBatch.getSelectedItem()).equalsIgnoreCase("Select Batch")){
            Toast.makeText(DatesheetActivity.this, "Please select batch", Toast.LENGTH_SHORT).show();
            return;
        }
        if (view.getId()==R.id.datesheet_add_btn){
            showAddDialog();
        }
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(DatesheetActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_datesheet);

        final Spinner subject = (Spinner) dialog.findViewById(R.id.dialog_datesheeet_subject);
        final EditText date = (EditText) dialog.findViewById(R.id.dialog_datesheeet_date);
        final EditText time = (EditText) dialog.findViewById(R.id.dialog_datesheeet_time);
        date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        time.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "HHMM";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 4; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 4){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the time is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));


                        day = day < 1 ? 1 : day > 12 ? 12 : day;
                        cal.set(Calendar.MONTH, mon-1);

                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, time e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        mon = mon > 60? 60:mon;
                        clean = String.format("%02d%02d",day, mon);
                    }

                    clean = String.format("%s:%s", clean.substring(0, 2),
                            clean.substring(2, 4));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    time.setText(current);
                    time.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ArrayAdapter<String> subectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        subject.setAdapter(subectAdapter);
        Button addBtn = (Button)dialog.findViewById(R.id.dialog_datesheet_btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectstr = (String)subject.getSelectedItem();
                String datestr = date.getText().toString().trim();
                String timestr = time.getText().toString().trim();
                 if ("Select Subject".equalsIgnoreCase(subjectstr)){
                     Toast.makeText(DatesheetActivity.this, "Please enter subject name", Toast.LENGTH_SHORT).show();
                 }else  if ("".equals(datestr)){
                     Toast.makeText(DatesheetActivity.this, "Please enter date", Toast.LENGTH_SHORT).show();
                 }else  if ("".equals(timestr)){
                     Toast.makeText(DatesheetActivity.this, "Please enter time", Toast.LENGTH_SHORT).show();
                 }else{
                     DatesheetItem datesheetItem = new DatesheetItem((String)spinnerDepartment.getSelectedItem(),(String)spinnerBatch.getSelectedItem()
                             ,subjectstr,datestr,timestr);
                     if (databaseHelper.createDatesheet(datesheetItem)!=-1){
                         datesheetItems.add(datesheetItem);
                         adapterDateSheetAdapter.notifyDataSetChanged();
                         date.setText("");
                         time.setText("");
                         subject.setSelection(0);
                         date.requestFocus();
                     }
                 }
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.dialog_datesheet_btn_done);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datesheetItems.clear();
                datesheetItems.addAll(databaseHelper.getDateSheet((String)spinnerDepartment.getSelectedItem(),(String)spinnerBatch.getSelectedItem()));
                adapterDateSheetAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        datesheetItems.clear();
        datesheetItems.addAll(databaseHelper.getDateSheet((String)spinnerDepartment.getSelectedItem(),(String)spinnerBatch.getSelectedItem()));
        adapterDateSheetAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
