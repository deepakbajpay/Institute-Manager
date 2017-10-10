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
import com.coderzpassion.studentfaculty.model.Marks;

import java.util.ArrayList;

/**
 * Created by coderzpassion on 15/05/17.
 */
public class AdapterAdmin extends BaseAdapter {

    private Context context;
    private ArrayList<Marks> marksArrayList;
    LayoutInflater inflater;
    ViewHolder holder = null;
//    private ArrayList<Marks> mDisplayedValues;
    private ArrayList<Marks> mOriginalValues; // Original Values


    public AdapterAdmin(Context context, ArrayList<Marks> marksArrayList) {
        this.context = context;
        this.mOriginalValues = marksArrayList;
//        mDisplayedValues=marksArrayList;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder = new ViewHolder();
            convertView =inflater.inflate(R.layout.adapter_admin,null,false);

            holder.subject =(TextView)convertView.findViewById(R.id.subject);
            holder.rollno =(TextView)convertView.findViewById(R.id.rollno);
            holder.marks =(TextView)convertView.findViewById(R.id.marks);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Marks marks= mOriginalValues.get(position);
        holder.subject.setText(marks.getSubject());
        holder.rollno.setText(marks.getMarks_to());
        holder.marks.setText(marks.getMarks_obtained()+"/"+marks.getTotal_marks());

        return convertView;
    }

    public class ViewHolder
    {
        TextView subject,rollno,marks,attendance;
    }

    @Override
    public int getCount() {
        System.out.println("AdapterAdmin.getCount " + mOriginalValues.size());
        return mOriginalValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mOriginalValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

/*
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Marks>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Marks> FilteredArrList = new ArrayList<Marks>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Marks>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                *//********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********//*
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getMarks_to();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Marks(mOriginalValues.get(i).getId(),mOriginalValues.get(i).getTotal_marks(),
                                    mOriginalValues.get(i).getMarks_obtained(),mOriginalValues.get(i).getMarks_by(),mOriginalValues.get(i).getMarks_to(),
                                    mOriginalValues.get(i).getSubject()));
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
    }*/

}