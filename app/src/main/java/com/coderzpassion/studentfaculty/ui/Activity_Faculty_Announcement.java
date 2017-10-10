package com.coderzpassion.studentfaculty.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.adapter.AdapterAnnoucements;
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

public class Activity_Faculty_Announcement extends BaseActivity {

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
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Announcements</font>"));

        databaseHelper=new DatabaseHelper(Activity_Faculty_Announcement.this);

        addmarks=(Button)findViewById(R.id.addmarks);
        addmarks.setText("Add Announcement");
        if (Constants.USER_TYPE_STUDENT.equalsIgnoreCase(SharedPrefs.getString(this,SharedPrefs.type))){
            addmarks.setVisibility(View.GONE);
        }
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

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

    public void showDialog(){
        final Dialog dialog = new Dialog(Activity_Faculty_Announcement.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_annoucement);

        final EditText annoucement = (EditText) dialog.findViewById(R.id.annoucement);


        Button addmarks = (Button) dialog.findViewById(R.id.addmarks);
        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(annoucement.getText().toString().equalsIgnoreCase(""))
                {
                    showToastMessage("Please enter valid Annoucement");
                }
                else
                {
                    databaseHelper.addAnnoucement(annoucement.getText().toString());
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

    public void setAdapter()
    {
        ArrayList<Marks> markses=databaseHelper.getAllAnnoucements();
        AdapterAnnoucements adapterMarks=new AdapterAnnoucements(Activity_Faculty_Announcement.this,markses);
        allmarks.setAdapter(adapterMarks);
    }
}
