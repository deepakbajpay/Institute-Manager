package com.coderzpassion.studentfaculty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.model.Student;

import java.util.ArrayList;

/**
 * Created by bajpa on 26-Sep-17.
 */

public class AdapterStudent  extends RecyclerView.Adapter<AdapterStudent.VH>{

    private Context context;
    private ArrayList<Student> faculties;

    public AdapterStudent(Context context, ArrayList<Student> faculties){
        this.context = context;
        this.faculties = faculties;
    }
    @Override
    public AdapterStudent.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_student_item,parent,false);
        return new AdapterStudent.VH(view);
    }

    @Override
    public void onBindViewHolder(AdapterStudent.VH holder, int position) {
        holder.name.setText(faculties.get(position).getName());
        holder.department.setText(faculties.get(position).getDepartment());
        holder.batch.setText(faculties.get(position).getBatch());
        holder.contact.setText(faculties.get(position).getContact());
        holder.email.setText(faculties.get(position).getEmail());
        holder.rollno.setText(faculties.get(position).getRollno());
    }

    @Override
    public int getItemCount() {
        System.out.println("AdapterTeacher.getItemCount " + faculties.size());
        return faculties.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView name,rollno,department,contact,email,batch;
        public VH(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txt_value_name);
            department = (TextView)itemView.findViewById(R.id.txt_value_department);
            contact = (TextView)itemView.findViewById(R.id.txt_value_contact);
            email = (TextView)itemView.findViewById(R.id.txt_value_email);
            batch = (TextView)itemView.findViewById(R.id.txt_value_batch);
            rollno = (TextView)itemView.findViewById(R.id.txt_value_rollno);

        }
    }
}
