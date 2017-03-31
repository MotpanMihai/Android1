package com.example.mihai.fontys_companion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ScheduleAdapter extends BaseAdapter {
    private List<Schedule> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ScheduleAdapter(Context theContext, List<Schedule> listData) {
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
        ScheduleAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_schedule_list_layout, null);
            holder = new ScheduleAdapter.ViewHolder();
            holder.course = (TextView) convertView.findViewById(R.id.textView_course);
            holder.room = (TextView) convertView.findViewById(R.id.textView2_room);
            holder.teacher = (TextView) convertView.findViewById(R.id.textView3_teacher);
            holder.start = (TextView) convertView.findViewById(R.id.textView4_start);
            holder.end = (TextView) convertView.findViewById(R.id.textView5_end);
            convertView.setTag(holder);
        } else {
            holder = (ScheduleAdapter.ViewHolder) convertView.getTag();
        }
        Schedule schedule = this.listData.get(position);
        holder.course.setText("Course: " + schedule.getCourse());
        holder.room.setText("Grade: " + schedule.getRoom());
        holder.teacher.setText("Date: " + schedule.getTeacher());
        holder.start.setText("Grade: " + schedule.getStart());
        holder.end.setText("Date: " + schedule.getEnd());
        return convertView;
    }

    static class ViewHolder {
        TextView course;
        TextView room;
        TextView teacher;
        TextView start;
        TextView end;
    }
}
