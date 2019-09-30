package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel2;
import www.softedgenepal.com.softedgenepalschool.R;

public class TeacherClassRoutineAdapter2 extends RecyclerView.Adapter<TeacherClassRoutineAdapter2.ViewHolder> {
    private Activity activity;
    private TeacherClassRoutineModel2 model;
    private int row;
    private int rowCount, colnCount;
    private int size;

    private int redColor, blueColor, greenColor;

    public TeacherClassRoutineAdapter2(Activity activity, TeacherClassRoutineModel2 model, int row, int rowCount, int colnCount) {
        this.activity = activity;
        this.model = model;
        this.row = row;
        this.rowCount = rowCount;
        this.colnCount = colnCount;
        size = colnCount;   //model.Detail.get(0).Detail.size() + 1;

        redColor = activity.getResources().getColor(R.color.red_500);
        blueColor = activity.getResources().getColor(R.color.colorPrimary);
        greenColor = activity.getResources().getColor(R.color.green_500);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View viewLineVertical, viewLineHorizontal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerTextView);
            viewLineVertical = itemView.findViewById(R.id.viewLineVertical);
            viewLineHorizontal = itemView.findViewById(R.id.viewLineHorizontal);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.classroutine_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int column) {
        TextView textView = viewHolder.textView;
        loadView(textView, column);

        if(row == rowCount - 1){
            viewHolder.viewLineVertical.setVisibility(View.VISIBLE);
        }

        if(column == colnCount - 1){
            viewHolder.viewLineHorizontal.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    private void loadView2(TextView textView, int column) {
        if (row == 0 && column == 0) {
            setText(textView, "Time\nDay", redColor);

        } else if (row == 0 && column < colnCount) {
            //todo work for days
            TeacherClassRoutineModel2.DayDetail dayDetail = model.Detail.get(column - 1);
            if (dayDetail != null) setText(textView, dayDetail.Day, blueColor);

        } else if (column == 0 && row < rowCount) {
            //todo work for time title
            TeacherClassRoutineModel2.DayDetail.TimeDetail timeDetail = model.Detail.get(0).Detail.get(row - 1);
            if (timeDetail != null) setText(textView, timeFormate(timeDetail.Time), greenColor);

        } else {
            //todo work for subject and other to show
            TeacherClassRoutineModel2.DayDetail.TimeDetail timeDetail = model.Detail.get(0).Detail.get(row - 1);
            if(timeDetail.Detail.size() > 0) {
                TeacherClassRoutineModel2.DayDetail.TimeDetail.FacultyDetail subjectDetail = timeDetail.Detail.get(0);
                if (subjectDetail != null) setText(textView, subjectDetail.SubjectNameEng, -1);
                else setText(textView, "", -1);
            }else {
                setText(textView, "", -1);
            }
        }
    }

    private void loadView(TextView textView, int column) {
        if (row == 0 && column == 0) {
            textView.setTextColor(activity.getResources().getColor(R.color.red_500));
            textView.setText("\t\tTime\nDay\t\t");

        } else if (row == 0 && column < colnCount) {
            //todo work for time title
            TeacherClassRoutineModel2.DayDetail.TimeDetail timeDetail = model.Detail.get(0).Detail.get(column - 1);
            if (timeDetail != null) setText(textView, timeFormate(timeDetail.Time), greenColor);

        } else if (column == 0 && row < rowCount) {
            //todo work for days
            TeacherClassRoutineModel2.DayDetail dayDetail = model.Detail.get(row - 1);
            if (dayDetail != null) setText(textView, dayDetail.Day, blueColor);

        } else {
            //todo work for subject and other to show
            TeacherClassRoutineModel2.DayDetail.TimeDetail timeDetail = model.Detail.get(0).Detail.get(column - 1);
            if(timeDetail.Detail.size() > 0) {
                TeacherClassRoutineModel2.DayDetail.TimeDetail.FacultyDetail subjectDetail = timeDetail.Detail.get(0);
                if (subjectDetail != null) {
                    String subject = subjectDetail.FacultyName;
                    subject = subject + "\n"+ subjectDetail.ClassName + ", "+subjectDetail.Section;
                    subject = subject + "\n"+ subjectDetail.SubjectNameEng;
                    setText(textView, subject, -1);
                }else setText(textView, "", -1);
            }else {
                setText(textView, "", -1);
            }
        }
    }

    private void setText(TextView textView, String string, int color) {
        if (color != -1) {
            textView.setTextColor(color);
        }
        textView.setText(string);
    }

    public String timeFormate(String time) {
        try {
            time = time.replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }

}
