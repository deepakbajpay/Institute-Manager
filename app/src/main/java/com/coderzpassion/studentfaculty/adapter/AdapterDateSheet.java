package com.coderzpassion.studentfaculty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.DatesheetItem;

import java.util.ArrayList;

/**
 * Created by bajpa on 23-Sep-17.
 */

public class AdapterDateSheet extends RecyclerView.Adapter<AdapterDateSheet.MyViewHolder > {

    ArrayList<DatesheetItem> datesheetItems;
    Context context;
    public AdapterDateSheet(Context context, ArrayList<DatesheetItem> datesheetItems){
        this.context = context;
        this.datesheetItems = datesheetItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_datesheet,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.subject.setText(datesheetItems.get(position).getSubject());
        holder.date.setText(datesheetItems.get(position).getDate());
        holder.time.setText(datesheetItems.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return datesheetItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView subject,date,time;
    public MyViewHolder(View itemView) {
        super(itemView);
        subject = (TextView) itemView.findViewById(R.id.datesheet_subject);
        date = (TextView)itemView.findViewById(R.id.datesheet_date);
        time = (TextView)itemView.findViewById(R.id.datesheet_time);
    }

    @Override
    public void onClick(View view) {

    }
}
}
