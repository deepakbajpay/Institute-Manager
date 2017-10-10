package com.coderzpassion.studentfaculty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Marks;

import java.util.ArrayList;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class AdapterAnnoucements extends BaseAdapter {

    private Context context;
    private ArrayList<Marks> marksArrayList;
    LayoutInflater inflater;
    ViewHolder holder = null;


    public AdapterAnnoucements(Context context, ArrayList<Marks> addressArrayList) {
        this.context = context;
        this.marksArrayList = addressArrayList;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder = new ViewHolder();
            convertView =inflater.inflate(R.layout.adapter_annoucement,null,false);

//            holder.subject =(TextView)convertView.findViewById(R.id.subject);
            holder.announcementBy =(TextView)convertView.findViewById(R.id.announcement_by);
            holder.announcementText =(TextView)convertView.findViewById(R.id.announcement_text);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Marks favourite= marksArrayList.get(position);
        String by = "By: "+favourite.getTotal_marks();
        holder.announcementBy.setText(by);
        holder.announcementText.setText(favourite.getSubject());
//        holder.rollno.setVisibility(View.GONE);
//        holder.marks.setVisibility(View.GONE);

//        holder.startcount.setImageDrawable();
//        holder.amount.setImageDrawable();

        return convertView;
    }

    public class ViewHolder
    {
        TextView announcementBy,announcementText;
    }

    @Override
    public int getCount() {
        return marksArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return marksArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}