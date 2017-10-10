package com.coderzpassion.studentfaculty.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by coderzpassion on 28/09/16.
 */
public class BaseActivity extends AppCompatActivity {

    Context baseActivity=this;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showProgressDialog()
    {
       if(mDialog==null)
       {
           mDialog = new ProgressDialog(baseActivity);
       }
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);
        if(!mDialog.isShowing()|| !((BaseActivity)baseActivity).isFinishing())
           mDialog.show();

    }

    public void hideProgressDialog()
    {
        if(mDialog!=null||!mDialog.isShowing())
            mDialog.dismiss();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void showToastMessage(String msg)
    {
        Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
