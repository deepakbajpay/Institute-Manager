package com.coderzpassion.studentfaculty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Marks;
import com.coderzpassion.studentfaculty.utils.Constants;

import java.util.ArrayList;

/**
 * Created by coderzpassion on 19/01/17.
 */

public class AdapterMarks extends BaseAdapter {

    private Context context;
    private ArrayList<Marks> marksArrayList;
    LayoutInflater inflater;
    ViewHolder holder = null;
    String type;


    public AdapterMarks(Context context, ArrayList<Marks> addressArrayList,String type) {
        this.context = context;
        this.marksArrayList = addressArrayList;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.type = type;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder = new ViewHolder();
            convertView =inflater.inflate(R.layout.adapter_marks,null,false);

            holder.subject =(TextView)convertView.findViewById(R.id.txt_subject);
            holder.mst =(TextView)convertView.findViewById(R.id.txt_mst);
            holder.rollno =(TextView)convertView.findViewById(R.id.rollno);
            holder.marks =(TextView)convertView.findViewById(R.id.marks);
            holder.lableMarks = (TextView)convertView.findViewById(R.id.label_marks);
            if (Constants.TYPE_ATTENDANCE.equalsIgnoreCase(type)){
                holder.mst.setVisibility(View.GONE);
                holder.lableMarks.setText("Attendance:");
            }
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Marks favourite= marksArrayList.get(position);

        holder.subject.setText(favourite.getSubject());
        holder.rollno.setText(favourite.getMarks_to());
        holder.marks.setText(favourite.getMarks_obtained()+"/"+favourite.getTotal_marks());
        if (!Constants.TYPE_ATTENDANCE.equalsIgnoreCase(type)){
            holder.mst.setText(favourite.getMst());
        }
//        holder.startcount.setImageDrawable();
//        holder.amount.setImageDrawable();

        return convertView;
    }

    public class ViewHolder
    {
        TextView subject,rollno,marks,mst,lableMarks;
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