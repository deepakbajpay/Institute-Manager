package com.coderzpassion.studentfaculty.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.utils.DatabaseHelper;

public class CollegeHeadInfo extends AppCompatActivity {

    TextView nameValue, contactValue, emailValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_head_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>College Head</font>"));

        nameValue = (TextView) findViewById(R.id.name_value);
        contactValue = (TextView) findViewById(R.id.contact_value);
        emailValue = (TextView) findViewById(R.id.email_value);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Faculty collegeHead = databaseHelper.getCollegeHeadInfo();
        nameValue.setText(collegeHead.getName());
        contactValue.setText(collegeHead.getContact());
        emailValue.setText(collegeHead.getEmail());

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

}
