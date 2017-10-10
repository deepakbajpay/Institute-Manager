package com.coderzpassion.studentfaculty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.coderzpassion.studentfaculty.R;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.model.Marks;

import java.util.ArrayList;

/**
 * Created by coderzpassion on 15/05/17.
 */

public class AdapterAdminTeacher extends BaseAdapter implements Filterable{

    private Context context;
    private ArrayList<Faculty> mDisplayedValues;
    private ArrayList<Faculty> mOriginalValues;
    LayoutInflater inflater;
    ViewHolder holder = null;


    public AdapterAdminTeacher(Context context, ArrayList<Faculty> allFaculty) {
        this.context = context;
        this.mOriginalValues = allFaculty;
        mDisplayedValues=allFaculty;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder = new ViewHolder();
            convertView =inflater.inflate(R.layout.adapter_admin_teachers,null,false);

            holder.subject =(TextView)convertView.findViewById(R.id.subject);
            holder.rollno =(TextView)convertView.findViewById(R.id.rollno);
            holder.marks =(TextView)convertView.findViewById(R.id.marks);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Faculty marks= mDisplayedValues.get(position);
        holder.subject.setText(marks.getName());
        holder.rollno.setText(marks.getSubject());
        holder.marks.setText(marks.getEmail());

        return convertView;
    }

    public class ViewHolder
    {
        TextView subject,rollno,marks,attendance;
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mDisplayedValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Faculty>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Faculty> FilteredArrList = new ArrayList<Faculty>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Faculty>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getSubject();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Faculty(mOriginalValues.get(i).getId(),mOriginalValues.get(i).getName(),
                                    mOriginalValues.get(i).getPassword(),mOriginalValues.get(i).getEmail(),mOriginalValues.get(i).getContact(),mOriginalValues.get(i).getSubject()
                                    ));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
