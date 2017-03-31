package com.example.mihai.fontys_companion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class GradesAdapter extends BaseAdapter {
    private List<Grades> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GradesAdapter(Context theContext, List<Grades> listData) {
        this.context = theContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(theContext);
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_grades_list_layout, null);
            holder = new ViewHolder();
            holder.course = (TextView) convertView.findViewById(R.id.textView_course);
            holder.grade = (TextView) convertView.findViewById(R.id.textView2_grade);
            holder.date = (TextView) convertView.findViewById(R.id.textView3_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Grades grade = this.listData.get(position);
        holder.course.setText("Course: " + grade.getCourse());
        holder.grade.setText("Grade: " + grade.getGrade());
        holder.date.setText("Date: " + grade.getDate().substring(0, 10));
        return convertView;
    }

    static class ViewHolder {
        TextView course;
        TextView grade;
        TextView date;
    }
}

