package com.coderzpassion.studentfaculty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.utils.Constants;

import java.util.ArrayList;

/**
 * Created by bajpa on 26-Sep-17.
 */

public class AdapterTeacher extends RecyclerView.Adapter<AdapterTeacher.VH>{

    private Context context;
    private ArrayList<Faculty> faculties;
    String type;

    public AdapterTeacher(Context context, ArrayList<Faculty> faculties,String type){
        this.context = context;
        this.faculties = faculties;
        this.type = type;
    }
    @Override
    public AdapterTeacher.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_teacher_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(AdapterTeacher.VH holder, int position) {
        holder.name.setText(faculties.get(position).getName());
        holder.department.setText(faculties.get(position).getDepartment());
        holder.subject.setText(faculties.get(position).getSubject());
        holder.contact.setText(faculties.get(position).getContact());
        holder.email.setText(faculties.get(position).getEmail());
        if (Constants.USER_TYPE_HOD.equalsIgnoreCase(type)) {
            holder.subject.setVisibility(View.GONE);
            holder.subjectLabel.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView name,department,contact,email,subject,subjectLabel;
        public VH(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txt_value_name);
            department = (TextView)itemView.findViewById(R.id.txt_value_department);
            contact = (TextView)itemView.findViewById(R.id.txt_value_contact);
            email = (TextView)itemView.findViewById(R.id.txt_value_email);
            subject = (TextView)itemView.findViewById(R.id.txt_value_subject);
            subjectLabel = (TextView)itemView.findViewById(R.id.txt_subject);

        }
    }
}